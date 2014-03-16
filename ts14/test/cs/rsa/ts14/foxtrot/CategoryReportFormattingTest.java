package cs.rsa.ts14.foxtrot;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class CategoryReportFormattingTest {

    String header = "-- Time spent on classes and categories --";
    String footer = "                          ===============";

    private CategoryReportImpl categoryReport;

    @Before
    public void setUp() throws Exception {
        categoryReport = new CategoryReportImpl();
    }

    @Test
    public void checkReportFormattingWhenNoInputIsGiven() throws Exception {

        ReportGenerator.CreateNull(categoryReport);

        String result = categoryReport.getResult();
        List<String> lines = new LinkedList<>(Arrays.asList(result.split("\\n")));
        Collection<Integer> validClassLineNumber = Arrays.asList(1, 2, 3, 4, 5);

        assertThat(lines.indexOf(header), equalTo(0));
        assertThat(lines.indexOf("research                   0.0 (  0%)"), isIn(validClassLineNumber));
        assertThat(lines.indexOf("teaching                   0.0 (  0%)"), isIn(validClassLineNumber));
        assertThat(lines.indexOf("misc                       0.0 (  0%)"), isIn(validClassLineNumber));
        assertThat(lines.indexOf("consulent                  0.0 (  0%)"), isIn(validClassLineNumber));
        assertThat(lines.indexOf("adm                        0.0 (  0%)"), isIn(validClassLineNumber));
        assertThat(lines.indexOf("Total:                     0.0 (0)"), equalTo(6));
        assertThat(lines.indexOf(footer), equalTo(7));

        System.out.println(result);
    }

    @Test
    public void verifyFormattingForLargeConsulentAndHugeClassPct() throws Exception {
        // ConsulentMix report

        List<Pair<String, Double>> input = new ArrayList<>();
        input.add(Pair.of("terna", 150.0));
        input.add(Pair.of("adm", 1.0));

        List<String> lines = buildReport(input);

        assertThat(lines.indexOf("consulent                150.0 (15000%)"), greaterThan(0));
        System.out.println(categoryReport.getResult());
    }

    @Test
    public void verifyFormattingForConsulentOnlyReport() throws Exception {

        List<Pair<String, Double>> input = new ArrayList<>();
        input.add(Pair.of("terna", 150.0));

        List<String> lines = buildReport(input);

        assertThat(lines.indexOf("consulent                150.0 (  0%)"), greaterThan(0));
        System.out.println(categoryReport.getResult());
    }

    @Test
    public void verifyPositioningForHugeValues() throws Exception {
        List<Pair<String, Double>> input = new ArrayList<>();

        input.add(Pair.of("saip", 1000.0));
        input.add(Pair.of("censor", 1000.0));
        input.add(Pair.of("sa", 1000.0));
        input.add(Pair.of("mtt", 1000.0));

        input.add(Pair.of("es", 1100.0));
        input.add(Pair.of("book2", 1100.0));
        input.add(Pair.of("n4c", 1100.0));

        input.add(Pair.of("syg", 1200.0));

        input.add(Pair.of("terna", 1300.0));

        input.add(Pair.of("itevmd", 1400.0));
        input.add(Pair.of("adm", 1400.0));


        List<String> lines = buildReport(input);

        assertThat(lines.indexOf("research                3300.0 ( 29%)"), greaterThan(0));
        assertThat(lines.indexOf("Total:                 11300.0 (12600)"), greaterThan(0));

        System.out.println(categoryReport.getResult());
    }

    private List<String> buildReport(List<Pair<String, Double>> input) {
        categoryReport.buildBegin();

        for (Pair<String, Double> p : input) {
            categoryReport.buildWorkSpecification(p.getLeft(), "-", p.getRight());
        }

        categoryReport.buildEnd();

        String result = categoryReport.getResult();
        return new LinkedList<>(Arrays.asList(result.split("\\n")));
    }

    @Test
    public void checkReportFormattingForDefaultReport() throws Exception {
        ReportGenerator.CreateDefault(categoryReport);

        List<String> resultLines = new LinkedList<>(Arrays.asList(categoryReport.getResult().split("\\n")));

        assertHeader(resultLines.get(0));
        assertFooter(resultLines.get(18));
        String expectedTotals = "Total:                    69.5 (71)";
        assertEquals(expectedTotals, resultLines.get(17));

        assertResearchLines(resultLines);
        assertTeachingLines(resultLines);
        assertMiscLines(resultLines);
        assertConsulentLines(resultLines);
        assertAdmLines(resultLines);

        System.out.println(categoryReport.getResult());
    }

    @Test
    public void testFullYearReport() throws Exception {
        ReportGenerator.CreateDefaultYearReport(categoryReport);
        System.out.println(categoryReport.getResult());

    }

    private void assertAdmLines(List<String> lines) {
        int classLine = lines.indexOf("adm                        8.5 ( 12%)");
        assertThat(classLine, greaterThanOrEqualTo(0));
        Collection<Integer> validCategoryLines;
        validCategoryLines = Arrays.asList(classLine + 1, classLine + 2);
        assertThat(lines.indexOf("    adm      :     6.0"), isIn(validCategoryLines));
        assertThat(lines.indexOf("    itevmd   :     2.5"), isIn(validCategoryLines));
    }

    private void assertConsulentLines(List<String> lines) {
        int classLine = lines.indexOf("consulent                  1.5 (  2%)");
        assertThat(classLine, greaterThanOrEqualTo(0));
        assertThat(lines.indexOf("    terna    :     1.5"), equalTo(classLine + 1));
    }

    private void assertMiscLines(List<String> lines) {
        int classLine = lines.indexOf("misc                       4.5 (  6%)");
        assertThat(classLine, greaterThanOrEqualTo(0));
        assertThat(lines.indexOf("    syg      :     4.5"), equalTo(classLine + 1));
    }

    private void assertTeachingLines(List<String> lines) {
        int classLine = lines.indexOf("teaching                  47.0 ( 68%)");
        assertThat(classLine, greaterThanOrEqualTo(0));
        Collection<Integer> validCategoryLines;
        validCategoryLines = Arrays.asList(classLine + 1, classLine + 2, classLine + 3, classLine + 4);
        assertThat(lines.indexOf("    censor   :     0.5"), isIn(validCategoryLines));
        assertThat(lines.indexOf("    mtt      :     3.0"), isIn(validCategoryLines));
        assertThat(lines.indexOf("    sa       :    41.5"), isIn(validCategoryLines));
        assertThat(lines.indexOf("    saip     :     2.0"), isIn(validCategoryLines));
    }

    private void assertResearchLines(List<String> lines) {
        int classLine = lines.indexOf("research                   9.5 ( 14%)");
        assertThat(classLine, greaterThanOrEqualTo(0));
        Collection<Integer> validCategoryLines = Arrays.asList(classLine + 1, classLine + 2, classLine + 3);
        assertThat(lines.indexOf("    book2    :     1.0"), isIn(validCategoryLines));
        assertThat(lines.indexOf("    es       :     4.5"), isIn(validCategoryLines));
        assertThat(lines.indexOf("    n4c      :     4.0"), isIn(validCategoryLines));
    }

    private void assertFooter(String s) {
        assertEquals(footer, s);
    }

    private void assertHeader(String s) {
        assertEquals(header, s);
    }

}
