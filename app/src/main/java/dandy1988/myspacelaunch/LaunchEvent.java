package dandy1988.myspacelaunch;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "net"
})

public class LaunchEvent {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("net")
    private String net;
    @JsonProperty("vidURLs")
    private List<String> vidURLs;
    @JsonProperty("infoURLs")
    private List<String> infoURLs;



    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("net")
    public void setNet(String net) {
        this.net = net;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("net")
    public String getNet() {
        return net;
    }


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty("vidURLs")
    public List<String> getVidURLs() {
        return vidURLs;
    }

    @JsonProperty("vidURLs")
    public void setVidURLs(List<String> vidURLs) {
        this.vidURLs = vidURLs;
    }

    @JsonProperty("infoURLs")
    public List<String> getInfoURLs() {
        return infoURLs;
    }

    @JsonProperty("infoURLs")
    public void setInfoURLs(List<String> infoURLs) {
        this.infoURLs = infoURLs;
    }
}
