package cs.rsa.ts14.foxtrot;


import cs.rsa.ts14.framework.ClassType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static cs.rsa.ts14.framework.ClassType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class CategoryReportImplTest {
    private CategoryReportImpl categoryReport;

    @Before
    public void setUp() throws Exception {
        categoryReport = new CategoryReportImpl();
    }

    @Test
    public void shouldCreateEmptyCategoryReportWhenNoInputIsGiven() throws Exception {
        categoryReport.buildBegin();
        categoryReport.buildEnd();

        assertAllZero();
    }

    private void assertAllZero() {
        assertTotal(0);
        assertClassSum(0, ADM);
        assertClassSum(0, CONSULENT);
        assertClassSum(0, MISC);
        assertClassSum(0, RESEARCH);
        assertClassSum(0, TEACHING);

        assertPercentage(0, ADM);
        assertPercentage(0, CONSULENT);
        assertPercentage(0, MISC);
        assertPercentage(0, RESEARCH);
        assertPercentage(0, TEACHING);
    }

    @Test
    public void shouldAddHoursToTeaching() {
        assertHoursAddedTo(1, "censor", TEACHING);
        assertHoursAddedTo(2.5, "sa", TEACHING);
        assertHoursAddedTo(4, "saip", TEACHING);
        assertHoursAddedTo(6, "mtt", TEACHING);
    }

    @Test
    public void shouldAddHoursToADM() {
        assertHoursAddedTo(1, "itevmd", ADM);
        assertHoursAddedTo(1, "adm", ADM);
    }

    @Test
    public void shouldAddHoursToRESEARCH() {
        assertHoursAddedTo(1, "es", RESEARCH);
        assertHoursAddedTo(1, "book2", RESEARCH);
        assertHoursAddedTo(1, "n4c", RESEARCH);
    }

    @Test
    public void shouldAddHoursToMISC() {
        assertHoursAddedTo(1, "syg", MISC);
    }

    @Test
    public void shouldAddHoursToCONSULENT() {
        assertConsulentHoursAdded(11, "terna");
    }

    @Test
    public void shouldSummarizeToCategoryClassSumAndTotals() throws Exception {
        List<Pair<String, Double>> input = new ArrayList<>();
        input.add(Pair.of("adm", 11.0));
        input.add(Pair.of("adm", 12.0));

        input.add(Pair.of("sa", 21.0));
        input.add(Pair.of("sa", 22.0));

        input.add(Pair.of("es", 31.0));
        input.add(Pair.of("es", 32.0));

        ReportGenerator.buildFrom(categoryReport, input);


        assertClassSum(63, RESEARCH);
        assertCategory(63, "es");

        assertClassSum(43, TEACHING);
        assertCategory(43, "sa");

        assertClassSum(23, ADM);
        assertCategory(23, "adm");

        assertClassSum(0, MISC);
        assertClassSum(0, CONSULENT);

        assertTotal(129);
        assertTotalX(129);

        assertPercentage(63, 129, RESEARCH);
        assertPercentage(43, 129, TEACHING);
        assertPercentage(23, 129, ADM);
        assertPercentage(0, MISC);
        assertPercentage(0, CONSULENT);
    }

    @Test
    public void shouldSummarizeToCategoryClassSumAndTotalsUsingConsulent() throws Exception {
        List<Pair<String, Double>> input = new ArrayList<>();
        input.add(Pair.of("adm", 11.0));
        input.add(Pair.of("adm", 12.0));

        input.add(Pair.of("terna", 21.0));
        input.add(Pair.of("terna", 22.0));

        input.add(Pair.of("es", 31.0));
        input.add(Pair.of("es", 32.0));

        ReportGenerator.buildFrom(categoryReport, input);


        assertClassSum(63, RESEARCH);
        assertCategory(63, "es");

        assertClassSum(43, CONSULENT);
        assertCategory(43, "terna");

        assertClassSum(23, ADM);
        assertCategory(23, "adm");

        assertClassSum(0, MISC);
        assertClassSum(0, TEACHING);

        assertTotal(129);
        assertTotalX(86);

        assertPercentage(63, 86, RESEARCH);
        assertPercentage(43, 86, CONSULENT);
        assertPercentage(0, MISC);
        assertPercentage(0, TEACHING);

        System.out.println(categoryReport.getResult());

    }

    @Test
    public void shouldCalculateZeroPercentageForZeroTotalX() throws Exception {
        List<Pair<String, Double>> input = new ArrayList<>();
        input.add(Pair.of("terna", 1.0));
        input.add(Pair.of("terna", 2.0));

        ReportGenerator.buildFrom(categoryReport, input);


        assertClassSum(3, CONSULENT);
        assertCategory(3, "terna");

        assertClassSum(0, RESEARCH);
        assertClassSum(0, ADM);
        assertClassSum(0, MISC);
        assertClassSum(0, TEACHING);

        assertTotal(3);
        assertTotalX(0);

        assertPercentage(0, RESEARCH);
        assertPercentage(0, CONSULENT);
        assertPercentage(0, ADM);
        assertPercentage(0, MISC);
        assertPercentage(0, TEACHING);

        System.out.println(categoryReport.getResult());

    }


    private void assertPercentage(double val, double tot, ClassType type) {
        assertPercentage(100*val/tot, type);
    }

    private void assertConsulentHoursAdded(double hours, String category) {
        categoryReport.buildBegin();
        categoryReport.buildWorkSpecification(category, "-", hours);
        categoryReport.buildEnd();

        assertTotal(hours);
        assertTotalX(0);

        assertClassSum(hours, CONSULENT);

        for (ClassType ct : ClassType.values()) {
            if (ct != CONSULENT) {
                assertClassSum(0, ct);
            }
        }

        assertCategory(hours, category);
        assertPercentage(0, CONSULENT);
    }

    @Test
    public void shouldSumValuesForCatogories() {
        ReportGenerator.CreateDefault(categoryReport);

        assertClassSum(47, TEACHING);
        assertClassSum(9.5, RESEARCH);
        assertClassSum(4.5, MISC);
        assertClassSum(1.5, CONSULENT);
        assertClassSum(8.5, ADM);
        assertTotal(71.0);
    }

    @Test
    public void shouldCalculatePercentages() {
        ReportGenerator.CreateDefault(categoryReport);

        double totalExcludingConsulent = 69.5;
        double teachingPercentage = 100 * 47 / totalExcludingConsulent;
        double researchPercentage = 100 * 9.5 / totalExcludingConsulent;
        double miscPercentage = 100 * 4.5 / totalExcludingConsulent;
        double consulentPercentage = 100 * 1.5 / totalExcludingConsulent;
        double admPercentage = 100 * 8.5 / totalExcludingConsulent;

        assertPercentage(teachingPercentage, TEACHING);
        assertPercentage(researchPercentage, RESEARCH);
        assertPercentage(miscPercentage, MISC);
        assertPercentage(consulentPercentage, CONSULENT);
        assertPercentage(admPercentage, ADM);
    }

    @Test
    public void shouldSummarizeDataFrom52Weeks() throws Exception {
        ReportGenerator.CreateDefaultYearReport(categoryReport);

        assertClassSum(1638, TEACHING);
        assertClassSum(52, RESEARCH);
        assertCategory(884.0, "sa");

        assertClassSum(26, MISC);
        assertClassSum(0, CONSULENT);
        assertClassSum(208, ADM);
        assertTotal(1924.0);

        System.out.println(categoryReport.getResult());
    }

    private void assertPercentage(double percentage, ClassType type) {
        assertEquals(percentage, categoryReport.categoryPercentage(type), 0.01);
    }

    @Test
    public void shouldIgnoreCatogoryWhichCantBeMapped() {
        categoryReport.buildBegin();
        categoryReport.buildWorkSpecification("illegal", "-", 10);
        categoryReport.buildEnd();

        assertAllZero();
    }

    private void assertHoursAddedTo(double hours, String category, ClassType type) {
        categoryReport.buildBegin();
        categoryReport.buildWorkSpecification(category, "-", hours);
        categoryReport.buildEnd();

        assertTotal(hours);
        assertTotalX(hours);

        assertClassSum(hours, type);
        for (ClassType ct : ClassType.values()) {
            if (ct != type) {
                assertClassSum(0, ct);
            }
        }

        assertCategory(hours, category);

        assertPercentage(100, type);
    }

    private void assertCategory(double h, String catId) {
        assertEquals(h, categoryReport.categoryTotal(catId), 0.001);
    }

    private void assertClassSum(double val, ClassType type) {
        assertEquals(val, categoryReport.classTotal(type), 0.001);
    }

    private void assertTotal(double expected) {
        assertEquals(expected, categoryReport.getTotal(), 0.001);
    }

    private void assertTotalX(double expected) {
        assertEquals(expected, categoryReport.getTotalExcl(), 0.001);
    }

}
