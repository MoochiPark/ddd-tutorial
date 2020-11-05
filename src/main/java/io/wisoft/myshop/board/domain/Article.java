package io.wisoft.myshop.board.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "article")
@SecondaryTable(
    name = "article_content",
    pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
)
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @AttributeOverrides({
      @AttributeOverride(name = "content",
          column = @Column(table = "article_content")),
      @AttributeOverride(name = "contentType",
          column = @Column(table = "article_content"))
  })
  @Embedded
  private ArticleContent content;

}
