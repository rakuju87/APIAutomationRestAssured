package API.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weather {
  private String valid_date;
  private double UV;
  private double min_temp;
  private double max_temp;
}
