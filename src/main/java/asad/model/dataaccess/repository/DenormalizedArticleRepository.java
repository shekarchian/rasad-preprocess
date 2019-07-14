package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.Article;
import asad.model.dataaccess.entity.DenormalizedLemmatizedArticleText;
import org.springframework.data.repository.CrudRepository;

public interface DenormalizedArticleRepository extends CrudRepository<DenormalizedLemmatizedArticleText, Integer> {

    DenormalizedLemmatizedArticleText findByArticleId(Integer id);

}
