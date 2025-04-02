package tdtu.edu.vn.shoes_store.model;

public class DetailCheckout {
    private Long productId;
    private int quantity;
    private int size;

    public DetailCheckout(Long productId, int quantity, int size) {
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
    }

    public DetailCheckout() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}