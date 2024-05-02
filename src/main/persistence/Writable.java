package persistence;

import org.json.JSONObject;

// Interface for writing to the JSON file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
