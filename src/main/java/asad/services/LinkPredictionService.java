package asad.services;

import asad.model.PredictedLink;
import asad.model.PredictedLinksRequest;
import asad.model.TopicProbability;
import asad.model.dataaccess.entity.Article;
import asad.model.dataaccess.entity.Author;
import asad.model.wrapper.ArticleWrapper;
import asad.model.wrapper.AuthorWrapper;

import java.util.List;
import java.util.Set;

public interface LinkPredictionService {
    AuthorWrapper getAuthorInfo(Integer code);
    Set<ArticleWrapper> getAuthorArticles(Integer id);
    ArticleWrapper getArticleInfo(Integer id);
    Set<String> getArticleTopicCcs(String code);

    Set<String> getArticleTopicKeywords(String code);

    List<PredictedLink> getPredictedLinks(PredictedLinksRequest predictedLinksRequest);
    Set<String> getAuthorTopicKeywords(String code);
    Set<String> getAuthorTopicCcs(String code);
    Set<Author> getCoAuthors(String code);

    List<Author> getPredictedCoAuthors(Integer code);

    Set<Article> getRelatedArticles(String code);

    List<Article> getPredictedRelatedArticles(Integer code);

    List<String> getAuthorTopic(String code);

    List<String> getArticleTopic(String code);


    List<TopicProbability> getAuthorTopicProbability(String code);

    List<TopicProbability> getArticleTopicProbability(String code);


}
