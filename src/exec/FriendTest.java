package exec;

import javanpst.data.structures.dataTable.DataTable;
import javanpst.tests.multiple.friedmanTest.FriedmanTest;
import javanpst.tests.multiple.pageTest.PageTest;

public class FriendTest {

    public static void main(String[] args) {
        double samples [][] = {
                {0.9651163087695896,0.9719387341588358,0.9650521211768682},
                {0.718,0.716,0.722},
                {0.9555555555555556,0.9377777777777777,0.94},
                {0.6264477068824895,0.6366329966329967,0.6347416190894452},
                {0.8549302418961093,0.8529337440692283,0.8543286193247782},
                {0.8483091787439613,0.8512077294685991,0.8560386473429952},
                {0.6092021324658895,0.6246968464805277,0.6127658805457668},
                {0.8767352092352092,0.8664069264069264,0.846948051948052},
                {0.8074074074074074,0.8098765432098766,0.8074074074074074},
                {0.6749953314659197,0.6793697478991596,0.6872222222222223},
                {0.5326741470317322,0.5149715903005376,0.5823915668381613},
                {0.7068100358422938,0.7099641577060932,0.7064516129032258},
                {0.7770350564468212,0.7768568033273915,0.7769756387403446},
                {0.8746564763751438,0.8757843474659973,0.87789193590357},
                {0.5063888888888889,0.5190277777777778,0.4626388888888889},
                {0.7187544911229121,0.7071609676872834,0.7252811515969412},
                {0.4736854201139915,0.4594165594165594,0.4747732426303855},
                {0.9607843137254902,0.9606753812636165,0.9549019607843138},
                {0.9336219336219337,0.9275613275613276,0.9383116883116883},
                {0.5627051220933311,0.5619005243921819,0.5527434362695764},
        };

        DataTable data = new DataTable(samples);

        PageTest page = new PageTest(data);
        FriedmanTest friedman = new FriedmanTest(data);

        friedman.doTest();

        //Print results of Friedman test
        System.out.println("Results of Friedman test:\n"+friedman.printReport());

        //print results of the multiple comparisons procedure
        System.out.println("Results of the multiple comparisons procedure:\n"+friedman.printMultipleComparisonsProcedureReport());

        System.out.println(friedman.getPValue());

/*
        System.out.println();
        System.out.println("*******************");
        System.out.println("*******************");
        System.out.println("*******************");
        System.out.println();

        //Run Page test
        page.doTest();

        //Print results of Page test
        System.out.println("Results of Page test:\n"+page.printReport());
*/
    }
}
