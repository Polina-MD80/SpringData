package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownDto {
    //•	name – a char sequence with minimum length 2. The name is unique.
    //•	population – a number (must be positive).
    //•	guide – Long and detailed description of all known places
    @Expose
    private String name;
    @Expose
    private Integer population;
    @Expose
    private String guide;

    public TownDto() {
    }

    @Size(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @NotBlank
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
