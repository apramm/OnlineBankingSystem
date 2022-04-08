package persistence;

import org.json.JSONObject;

// This is a Writable interface for Json
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}

