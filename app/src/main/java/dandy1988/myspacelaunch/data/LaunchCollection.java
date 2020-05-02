package dandy1988.myspacelaunch.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LaunchCollection implements Serializable {

    private static final long serialVersionUID = -390805629858922784L;

    @JsonProperty("launches")
    private List<LaunchEvent> launches;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("launches")
    public List<LaunchEvent> getLaunches() {
        return launches;
    }

    @JsonProperty("launches")
    public void setLaunches(List<LaunchEvent> launches) {
        this.launches = launches;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}

