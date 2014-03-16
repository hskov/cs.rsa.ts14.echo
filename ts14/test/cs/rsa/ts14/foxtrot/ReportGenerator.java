package cs.rsa.ts14.foxtrot;

import cs.rsa.ts14.foxtrot.CategoryReportImpl;
import cs.rsa.ts14.foxtrot.Pair;
import cs.rsa.ts14.framework.ReportBuilder;

import java.util.List;

public class ReportGenerator {
    public static void CreateDefault(CategoryReportImpl report) {
        report.buildBegin();
        report.buildAssignment("HoursOvertime", 502.2);
        report.buildAssignment("Year", 2013);

        // Week 1
        report.buildWeekSpecification(1,3,0);

        report.buildWeekDaySpecification("Wed", "Ca");
        report.buildWorkSpecification("adm", "-", 1);
        report.buildWorkSpecification("sa", "exam", 3);
        report.buildWorkSpecification("sa", "exam", 1);
        report.buildWorkSpecification("sa", "exam", 2);

        report.buildWeekDaySpecification("Thu" ,"No");
        report.buildWorkSpecification("syg", "-", 4.5);
        report.buildWorkSpecification("sa", "exam", 3);

        report.buildWeekDaySpecification("Fri" ,"Ho");
        report.buildWorkSpecification("sa", "exam", 1);
        report.buildWorkSpecification("n4c", "-", 1);
        report.buildWorkSpecification("itevmd", "-", 1);
        report.buildWorkSpecification("mtt", "-", 1);
        report.buildWorkSpecification("mtt", "plan", 2);
        report.buildWorkSpecification("saip", "plan", 1.5);


        // Week 2
        report.buildWeekSpecification(2, 5, 0);

        report.buildWeekDaySpecification("Mon" ,"Ca");
        report.buildWorkSpecification("sa", "exam", 4);
        report.buildWorkSpecification("sa", "exam", 2);
        report.buildWorkSpecification("adm", "-", 1);

        report.buildWeekDaySpecification("Tue" ,"Ca");
        report.buildWorkSpecification("sa", "exam", 3.5);
        report.buildWorkSpecification("sa", "exam", 2.5);
        report.buildWorkSpecification("adm", "-", 0.5);
        report.buildWorkSpecification("itevmd", "-", 0.5);

        report.buildWeekDaySpecification("Wed" ,"Ca");
        report.buildWorkSpecification("adm", "-", 0.5);
        report.buildWorkSpecification("sa", "exam", 3.5);
        report.buildWorkSpecification("sa", "exam", 3);
        report.buildWorkSpecification("itevmd", "-", 0.5);

        report.buildWeekDaySpecification("Thu" ,"Ca");
        report.buildWorkSpecification("adm", "-", 0.5);
        report.buildWorkSpecification("sa", "exam", 3.5);
        report.buildWorkSpecification("sa", "exam", 4);

        report.buildWeekDaySpecification("Fri" ,"Ca");
        report.buildWorkSpecification("adm", "-", 0.5);
        report.buildWorkSpecification("censor", "-", 0.5);
        report.buildWorkSpecification("n4c", "e2e", 3);
        report.buildWorkSpecification("sa", "protokol", 2);
        report.buildWorkSpecification("saip", "-", 0.5);
        report.buildWorkSpecification("itevmd", "-", 0.5);
        report.buildWorkSpecification("sa", "social", 1.5);

        report.buildWeekDaySpecification("Sat" ,"No");
        report.buildWorkSpecification("sa", "hotciv", 1);
        report.buildWorkSpecification("es", "litt", 1.5);

        // Week 3
        report.buildWeekSpecification(3, 5, 0);

        report.buildWeekDaySpecification("Mon" ,"Ca");
        report.buildWorkSpecification("adm", "-", 0.5);
        report.buildWorkSpecification("es", "-", 0.5);
        report.buildWorkSpecification("sa", "-", 1);
        report.buildWorkSpecification("adm", "-", 1.5);
        report.buildWorkSpecification("book2", "errata", 1);
        report.buildWorkSpecification("es", "-", 2.5);
        report.buildWorkSpecification("terna", "course", 1.5);

        report.buildEnd();

    }


    public static void CreateNull(CategoryReportImpl report) {
        report.buildBegin();

        report.buildEnd();
    }

    public static void CreateDefaultYearReport(CategoryReportImpl report) {
        report.buildBegin();
        for(int w = 1; w <= 52; w++)
            addDefaultWeek(report, w);
        report.buildEnd();
    }

    private static void addDefaultWeek(CategoryReportImpl report, int weeknum) {
        // Week 1
        report.buildWeekSpecification(weeknum, 5, 0);

        report.buildWeekDaySpecification("Mon", "Ca");
        report.buildWorkSpecification("adm", "-", 1);
        report.buildWorkSpecification("sa", "exam", 3);
        report.buildWorkSpecification("mtt", "exam", 2);
        report.buildWorkSpecification("sa", "exam", 2);

        report.buildWeekDaySpecification("Tue" ,"No");
        report.buildWorkSpecification("syg", "-", 0.5);
        report.buildWorkSpecification("sa", "exam", 1.5);
        report.buildWorkSpecification("mtt", "exam", 6);

        report.buildWeekDaySpecification("Wed" ,"Ho");
        report.buildWorkSpecification("sa", "exam", 1);
        report.buildWorkSpecification("n4c", "-", 1);
        report.buildWorkSpecification("itevmd", "-", 1);
        report.buildWorkSpecification("mtt", "-", 1);
        report.buildWorkSpecification("mtt", "plan", 2.5);
        report.buildWorkSpecification("saip", "plan", 1.5);

        report.buildWeekDaySpecification("Thu", "Ca");
        report.buildWorkSpecification("adm", "-", 1);
        report.buildWorkSpecification("sa", "exam", 3);
        report.buildWorkSpecification("sa", "exam", 2);
        report.buildWorkSpecification("censor", "-", 1.5);

        report.buildWeekDaySpecification("Fri", "Ca");
        report.buildWorkSpecification("adm", "-", 1);
        report.buildWorkSpecification("sa", "exam", 3);
        report.buildWorkSpecification("sa", "exam", 1.5);
    }

    public static void buildFrom(ReportBuilder report, List<Pair<String, Double>> input) {
        report.buildBegin();

        for (Pair<String, Double> p : input) {
            report.buildWorkSpecification(p.getLeft(), "-", p.getRight());
        }

        report.buildEnd();

    }
}
