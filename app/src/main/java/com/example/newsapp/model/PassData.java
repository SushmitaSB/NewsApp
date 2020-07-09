package com.example.newsapp.model;


import com.example.newsapp.servermodel.ServerManager;

public class PassData {
    public void PassDataToActivity(int position, ServerManager serverManager){
        switch (position){
            case 0: serverManager.fetchDataFromServer();
                break;
            case 1: serverManager.fetchDataforUpdateHeadline();
                break;
            case 2:  serverManager.fetchDataforUpdateNewsPojo();
                break;
            case 3:  serverManager.fetchDataForUpdateTechCrunch();
                break;
            case 4: serverManager.fetchDataForUpdateWallStreetJournal();
                break;
        }
    }
}
