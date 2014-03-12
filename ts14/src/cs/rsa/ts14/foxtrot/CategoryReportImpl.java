package cs.rsa.ts14.foxtrot;

import cs.rsa.ts14.framework.ClassType;
import cs.rsa.ts14.framework.ReportBuilder;
import cs.rsa.ts14.standard.ClassMap;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class CategoryReportImpl implements ReportBuilder {
    Map<ClassType, Double> hoursPerCategory;
    Map<ClassType, Map<String, Double>> categoryHours;
    double totalExclusive;
    double total;
    private String header = "-- Time spent on classes and categories --";
    private String footer = "                          ===============";
    private String result;

    @Override
    public void buildBegin() {
        hoursPerCategory = new TreeMap<>();
        categoryHours = new TreeMap<>();
        for (ClassType ct : ClassType.values()) {
            hoursPerCategory.put(ct, 0.0);
            categoryHours.put(ct, new TreeMap<String, Double>());
        }
    }

    @Override
    public void buildWeekSpecification(int weekNo, int countWorkdays, int countUsedVacationdays) {
    }

    @Override
    public void buildWorkSpecification(String category, String subCategory, double hours) {
        ClassType type = ClassMap.mapCategoryToClass(category);
        if (type == null)
            return;

        hoursPerCategory.put(type, hoursPerCategory.get(type) + hours);

        Map<String, Double> catHoursMap = categoryHours.get(type);
        if (catHoursMap.containsKey(category) == false)
            catHoursMap.put(category, new Double(0));

        catHoursMap.put(category, catHoursMap.get(category) + hours);

    }

    @Override
    public void buildWeekDaySpecification(String weekDay, String transportMode) {
    }

    @Override
    public void buildAssignment(String variable, double value) {
    }

    @Override
    public void buildEnd() {
        summarize();

        createFormattedReport();
    }

    private void createFormattedReport() {
        StringBuilder sb = new StringBuilder();

        sb.append(header).append("\n");

        appendClassAndCategoryDetails(sb);

        appendTotal(sb);

        sb.append(footer).append("\n");

        result = sb.toString();
    }

    private void appendTotal(StringBuilder sb) {
        String totalFormatter = "%-20s %9.1f (%.1f)";
        if (total % 1 == 0)
            totalFormatter = "%-20s %9.1f (%.0f)";
        sb.append(String.format(Locale.UK, totalFormatter, "Total:", totalExclusive, total)).append("\n");
    }

    private void appendClassAndCategoryDetails(StringBuilder sb) {
        for (ClassType ct : ClassType.values()) {
            sb.append(String.format(Locale.UK, "%-20s %9.1f (%3.0f%%)\n",
                    ct.toString().toLowerCase(), hoursPerCategory.get(ct), categoryPercentage(ct)));
            appendCategoryDetails(sb, ct);
        }
    }

    private void appendCategoryDetails(StringBuilder sb, ClassType ct) {
        Map<String, Double> catMap = categoryHours.get(ct);
        for (Map.Entry<String, Double> entry : catMap.entrySet()) {
            sb.append(String.format(Locale.UK, "    %-9s: %7.1f",
                    entry.getKey(), entry.getValue())).append("\n");
        }
    }

    private void summarize() {
        total = 0;
        totalExclusive = 0;
        for (ClassType ct : ClassType.values()) {
            total += hoursPerCategory.get(ct);
            if (ct != ClassType.CONSULENT)
                totalExclusive += hoursPerCategory.get(ct);
        }
    }

    @Override
    public String getResult() {
        return result;
    }

    public double getTotal() {
        return total;
    }

    public double classTotal(ClassType type) {
        return hoursPerCategory.get(type);
    }

    public double categoryTotal(String catId) {
        ClassType type = ClassMap.mapCategoryToClass(catId);
        Map<String, Double> catHoursMap = categoryHours.get(type);
        return catHoursMap.get(catId);
    }

    public double categoryPercentage(ClassType type) {
        if (totalExclusive == 0)
            return 0;

        return 100 * hoursPerCategory.get(type) / totalExclusive;
    }

    public double getTotalExcl() {
        return totalExclusive;
    }
}
