package com.swiatek.benford.graph

trait GraphData {
    String correctInputNoHeader =
            """
            Alabama\tAbbeville \t2930\t3\t10\t3.94383\n
            Alabama\tAdamsville \t4782\t3\t11\t7.83099\n
            """

    String inputWithSomeIncorrectValuesNoHeader =
            """
            Alabama\tAdamsville \trandom\t3\t11\t7.83099\n
            Alabama\tAddison \t709\t3\t8\t7.9844\n
            """

    String correctInputNoHeaderWithZeroEntry =
            """
            Alabama\tAbbeville \t0.930\t3\t10\t3.94383\n
            Alabama\tAdamsville \t4782\t3\t11\t7.83099\n
            """

}