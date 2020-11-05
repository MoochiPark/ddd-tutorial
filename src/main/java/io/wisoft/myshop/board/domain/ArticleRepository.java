package io.wisoft.myshop.board.domain;

public interface ArticleRepository {

  void save(Article article);

  Article findById(Long id);

}
