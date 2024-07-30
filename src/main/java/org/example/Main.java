package org.example;
import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.TableId;
public class Main {

  public static void main(String[] args) {
    String projectId = "google.com:cloud-bigtable-dev"; // my-gcp-project-id
    String instanceId = "laties-dp-testing"; // my-bigtable-instance-id
    String tableId = "fluffybunny"; // my-bigtable-table-id

    quickstart(projectId, instanceId, tableId);
  }

  public static void quickstart(String projectId, String instanceId, String tableId) {
    BigtableDataSettings settings =
        BigtableDataSettings.newBuilder().setProjectId(projectId).setInstanceId(instanceId).build();

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (BigtableDataClient dataClient = BigtableDataClient.create(settings)) {
      System.out.println("\nReading a single row by row key");
      Row row = dataClient.readRow(TableId.of(tableId), "r1");
      System.out.println("Row: " + row.getKey().toStringUtf8());
      for (RowCell cell : row.getCells()) {
        System.out.printf(
            "Family: %s    Qualifier: %s    Value: %s%n",
            cell.getFamily(), cell.getQualifier().toStringUtf8(), cell.getValue().toStringUtf8());
      }
    } catch (NotFoundException e) {
      System.err.println("Failed to read from a non-existent table: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error during quickstart: \n" + e.toString());
    }
  }
}