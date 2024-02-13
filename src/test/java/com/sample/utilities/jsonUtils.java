package com.sample.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jsonUtils {

    public Map<String, Map<String, Object>> ReadAssetTestData() {

        File file = new File("Resources/TestData/EntitlementMultiSubscription.json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Map<String, Map<String, Object>> dataMap = objectMapper.readValue(fileInputStream, HashMap.class);
            return dataMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    public JSONObject ReadAssetTestData_N() throws FileNotFoundException {

        File file = new File("Testdata/NestedObject.json");
        FileReader fileReader = new FileReader(file);

        JSONTokener jsonTokener = new JSONTokener(fileReader);
        JSONObject jsonObject = new JSONObject(jsonTokener);
        return jsonObject;
    }

    public void SampleUse(){
        Map<String, Map<String, Object>> dataMap = ReadAssetTestData();
        for (Map.Entry<String, Map<String, Object>> entry : dataMap.entrySet()) {
            String country = entry.getKey();
            Map<String, Object> details = entry.getValue();
            List<String> AssetTypes = new ArrayList<>();
            List<String> AssetTypes_NotPresent = new ArrayList<>();
            for (Map.Entry<String, Object> detailEntry : details.entrySet()) {
                if ((detailEntry.getValue() instanceof Boolean) && !(detailEntry.getValue().toString().equalsIgnoreCase("false"))) {
                    AssetTypes.add(detailEntry.getKey());
                } else if (detailEntry.getValue().toString().equalsIgnoreCase("false")) {
                    AssetTypes_NotPresent.add(detailEntry.getKey());
                }
            }

        }

    }

    public void AnotherSampleUse() throws FileNotFoundException {
        JSONObject dataMap = ReadAssetTestData_N();
        for(String key: dataMap.keySet()){
            JSONObject object = dataMap.getJSONObject(key);
            JSONObject identity = object.getJSONObject("Identity");
            JSONObject privacy = object.getJSONObject("Privacy");
            java.util.List<String> AvailableList = new  java.util.ArrayList<>();
            java.util.List<String> NotAvailableList = new  java.util.ArrayList<>();
            // Iterate through the Identity items
            for (String identityKey : identity.keySet()) {
                if (identity.getBoolean(identityKey)) {
                    AvailableList.add(identityKey);
                }
                else{
                    NotAvailableList.add(identityKey);
                }
            }


            AvailableList.clear();
            NotAvailableList.clear();
            for (String privacyKey : privacy.keySet()) {
                if (privacy.getBoolean(privacyKey)) {
                    AvailableList.add(privacyKey);
                }
                else{
                    NotAvailableList.add(privacyKey);
                }
            }



        }
    }

}
