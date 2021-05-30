import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {
    @SerializedName("lat")
    private Float latitude;

    @SerializedName("lgn")
    private Float longitude;
}