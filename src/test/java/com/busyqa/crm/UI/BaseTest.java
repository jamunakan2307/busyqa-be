package com.busyqa.crm.UI;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class BaseTest {


  public static boolean restoreDB(String dbName, String dbUserName, String dbPassword, String source) throws IOException, InterruptedException {
    String[] executeCmd =
            new String[] {
                    "C:\\xampp\\mysql\\bin\\mysql.exe ",
                    "--user=" + dbUserName,
                    "--password=" + dbPassword,
                    dbName,
                    "-e",
                    " source " + source
            };
    Process runtimeProcess;
    try {
      runtimeProcess = Runtime.getRuntime().exec(executeCmd);
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("Backup restored successfully");
        return true;
      }
      else {
        System.out.println("Could not restore the backup");
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }



}