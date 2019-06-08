package com.example.ss;

import android.util.Log;

import com.example.ss.HomeFragmentV2Package.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductSort {




//------------------------------------
    public ArrayList<ProductModel> ascendingSort(ArrayList<ProductModel> listOfModel) {

        int n = listOfModel.size();
        ProductModel temp;

        for(int i = 0; i < n; i++) {
            for(int j=1; j < (n-i); j++) {
                if(listOfModel.get(j-1).getProductNumberAsInt() > listOfModel.get(j).getProductNumberAsInt()) {
                    temp = listOfModel.get(j-1);
                    listOfModel.add(j-1,listOfModel.get(j));
                    listOfModel.add(j,temp);
                }
            }
        }

    checkSort(listOfModel);

        return listOfModel;
    }


    void checkSort(List<ProductModel> listOfModel){


        for (int i = 0; i <listOfModel.size() ; i++) {

             Log.e("checkSort",listOfModel.get(i).getProductNumberAsInt()+" tested");


        }
    }

    //---------------------------

}

