package com.swiatek.benford.document

import java.time.LocalDateTime

trait DocumentData {
    String correctInput =
            "State\tTown\t7_2009\t3\t4\t8.40188\n" +
                    "Alabama\tAbbeville \t2930\t3\t10\t3.94383\n" +
                    "Alabama\tAdamsville \t4782\t3\t11\t7.83099\n"

    String correctInputWithBOM =
            "State\tTown\t\ufeff7_2009\t3\t4\t8.40188\n" +
                    "Alabama\tAbbeville \t2930\t3\t10\t3.94383\n" +
                    "Alabama\tAdamsville \t4782\t3\t11\t7.83099\n"

    String correctOneColumn =
            "7_2009\n" +
                    "0\n" +
                    "2500\n" +
                    "947\n" +
                    "441\n" +
                    "9507\n" +
                    "3591\n" +
                    "1830\n" +
                    "289\n" +
                    "3390\n" +
                    "573\n" +
                    "4280\n" +
                    "701\n" +
                    "385\n" +
                    "37019\n" +
                    "8642\n" +
                    "162\n" +
                    "3154\n" +
                    "8130\n" +
                    "154\n" +
                    "4819\n" +
                    "2506\n" +
                    "10670\n" +
                    "17955\n" +
                    "5340\n" +
                    "226\n" +
                    "477\n" +
                    "438"

    String incorrectInput =
            "State,Town,7_2009,3,4,8.40188\n" +
                    "Alabama,Abbeville ,2930,3,10,3.94383\n" +
                    "Alabama,Adamsville ,4782,3,11,7.83099\n" +
                    "Alabama,Addison ,709,3,8,7.9844\n"

    UploadedDocumentEntity getTestDocumentEntity() {
        return new UploadedDocumentEntity(1, "Test", LocalDateTime.now(), correctInput.getBytes())
    }
}