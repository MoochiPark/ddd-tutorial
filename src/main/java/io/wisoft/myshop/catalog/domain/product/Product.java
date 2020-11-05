package io.wisoft.myshop.catalog.domain.product;


import io.wisoft.myshop.common.model.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

  @EmbeddedId
  private ProductId id;
  private String name;

  private Money price;
  private String detail;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
      orphanRemoval = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  @OrderColumn(name = "list_idx")
  private List<Image> images = new ArrayList<>();

  public Product(ProductId id, String name, Money price, String detail, List<Image> images) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.detail = detail;
    this.images.addAll(images);
  }

  public List<Image> getImages() {
    return Collections.unmodifiableList(images);
  }

  public void changeImages(List<Image> newImages) {
    images.clear();
    images.addAll(newImages);
  }

  public String getFirstIamgeThumbnailPath() {
    if (images == null || images.isEmpty()) return null;
    return images.get(0).getThumbnailUrl();
  }
}
