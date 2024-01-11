package kuit2.server.dto;

public class RestaurantRequest {

    private Long id;
    private String name;
    private String category;
    private Integer minOrderFee;

    public RestaurantRequest(Long id, String name, String category, Integer minOrderFee) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.minOrderFee = minOrderFee;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMinOrderFee() {
        return minOrderFee;
    }

    public void setMinOrderFee(Integer minOrderFee) {
        this.minOrderFee = minOrderFee;
    }
}
