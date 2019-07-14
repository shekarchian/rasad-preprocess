package asad.services;

import asad.model.*;
import asad.model.dataaccess.entity.*;
import asad.model.dataaccess.repository.*;
import asad.model.wrapper.ArticleWrapper;
import asad.model.wrapper.AuthorWrapper;
import javassist.compiler.ast.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.event.ListDataEvent;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class LinkPredictionServiceImpl implements LinkPredictionService {


    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleKeywordRepository articleKeywordRepository;

    @Autowired
    private ArticleTopicDistributionRepository articleTopicDistributionRepository;

    @Autowired
    private AuthorTopicDistributionRepository authorTopicDistributionRepository;

    @Autowired
    private AuthorPredictedLinkRepository authorPredictedLinkRepository;

    @Autowired
    private ArticlePredictedLinkRepository articlePredictedLinkRepository;

    private List<Integer> rootTopicsId = Arrays.asList(new Integer[]{2902, 2922, 3057, 3374, 3450, 3558, 3664, 3793, 3979, 4210, 4345, 4402, 4616});

    public AuthorWrapper getAuthorInfo(Integer id) {
        Author author = authorRepository.findById(id).get();
        return new AuthorWrapper(author);
    }

    @Override
    public Set<ArticleWrapper> getAuthorArticles(Integer id) {
        Set<Article> articles = authorRepository.findAuthorArticlesAuthors(id).getArticles();
        Set<ArticleWrapper> articleWrappers = new HashSet<>();
        for (Article article : articles) {
            articleWrappers.add(new ArticleWrapper(article));
        }
        return articleWrappers;
    }

    @Override
    public ArticleWrapper getArticleInfo(Integer id) {
        Article article = articleRepository.findArticleCompleteInfo(id);
        return new ArticleWrapper(article);
    }

    @Override
    public Set<String> getArticleTopicKeywords(String code) {
        Set<ArticleKeyword> articleKeywords = articleKeywordRepository.findByArticle_Id(Integer.parseInt(code));
        Set<String> keywords = new HashSet<>();
        articleKeywords.forEach((keyword) -> keywords.add(keyword.getKeyword()));
        return keywords;
    }

    @Override
    public Set<String> getArticleTopicCcs(String code) {
        Set<Taxonomy> articleTaxonomies = articleRepository.findArticleTaxonomies(Integer.parseInt(code));
        Set<String> taxonomies = new HashSet<>();
        articleTaxonomies.forEach((taxonomy) -> {
            if (!rootTopicsId.contains(taxonomy.getId()) && !rootTopicsId.contains(taxonomy.getParent_taxonomy_class_id()))
                taxonomies.add(taxonomy.getTitle());
        });
        if (taxonomies.size() < 3) {
            articleTaxonomies.forEach((taxonomy) -> {
                if (rootTopicsId.contains(taxonomy.getParent_taxonomy_class_id()))
                    taxonomies.add(taxonomy.getTitle());
            });
        }
        return taxonomies;
    }

    @Override
    public Set<String> getAuthorTopicKeywords(String code) {
        Set<ArticleKeyword> articleKeywords = articleKeywordRepository.findAuthorKeywords(Integer.parseInt(code));
        Set<String> keywords = new HashSet<>();
        articleKeywords.forEach((keyword) -> keywords.add(keyword.getKeyword()));
        return keywords;
    }

    @Override
    public Set<String> getAuthorTopicCcs(String code) {
        Set<Taxonomy> authorTaxonomies = authorRepository.findAuthorTaxonomies(Integer.parseInt(code));
        Set<String> taxonomies = new HashSet<>();
        authorTaxonomies.forEach((taxonomy) -> {
            if (!rootTopicsId.contains(taxonomy.getId()) && !rootTopicsId.contains(taxonomy.getParent_taxonomy_class_id()))
                taxonomies.add(taxonomy.getTitle());
        });
        return taxonomies;
    }

    @Override
    public Set<Author> getCoAuthors(String code) {
        Set<Article> articles = authorRepository.findAuthorArticlesAuthors(Integer.parseInt(code)).getArticles();
        Map<Author, Integer> authorsMap = createMapOfAuthors(articles, Integer.parseInt(code));
        Map<Author, Integer> sortedAuthorsMap = sortByValue(authorsMap);
        return sortedAuthorsMap.keySet();
    }

    private Map<Author, Integer> createMapOfAuthors(Set<Article> articles, Integer authorId) {
        Map<Author, Integer> authorsMap = new HashMap<>();
        articles.forEach((article -> {
            Set<Author> articleAuthors = article.getAuthors();
            articleAuthors.forEach(author -> {
                if (author.getId().equals(authorId))
                    return;
                if (!authorsMap.containsKey(author)) {
                    authorsMap.put(author, 1);
                } else {
                    authorsMap.put(author, authorsMap.get(author) + 1);
                }
            });
        }));
        return authorsMap;
    }


    private static Map<Author, Integer> sortByValue(final Map<Author, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<Author, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    @Override
    public List<TopicProbability> getArticleTopicProbability(String code) {
        List<TopicProbability> topicProbabilities = new ArrayList<>();
        List<ArticleTopicDistribution> articleTopicDistributions = articleTopicDistributionRepository.findArticleTopic(
                Integer.parseInt(code));
        articleTopicDistributions.forEach(atd -> {
            topicProbabilities.add(new TopicProbability(atd.getTopic().getTopicCode(), atd.getProbability(),
                    Arrays.asList(atd.getTopic().getWordList().split(" "))));
        });
        return topicProbabilities;
    }


    @Override
    public List<String> getArticleTopic(String code) {
        List<String> topicTopWords = getArticleTopWordsOfTopic(code);
        Set<String> keyword = getArticleTopicKeywords(code);
        Set<String> ccs = getArticleTopicCcs(code);
        List<String> mergedTopics = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);
        keyword.forEach(w -> {
            mergedTopics.add(w);
            counter.getAndIncrement();
            if (counter.get() > 5)
                return;
        });
        counter.set(0);
        ccs.forEach(w -> {
            mergedTopics.add(w);
            counter.getAndIncrement();
            if (counter.get() > 5)
                return;
        });
        counter.set(0);
        topicTopWords.forEach(w -> {
            mergedTopics.add(w);
            counter.getAndIncrement();
            if (counter.get() > 5)
                return;
        });

        return mergedTopics;

    }

    private List<String> getArticleTopWordsOfTopic(String code) {
        Map<Double, String> topicProbabilitiesMap = new HashMap<>();
        List<ArticleTopicDistribution> articleTopicDistributions = articleTopicDistributionRepository.findArticleTopic(
                Integer.parseInt(code));
        articleTopicDistributions.forEach(atd -> {
            topicProbabilitiesMap.put(atd.getProbability(), atd.getTopic().getWordList());
        });
        List<String> sortedTopics = getSortedListFromTopicsMap(topicProbabilitiesMap);
        List<String> topWords = new ArrayList<>();
        String[] splitedWords = sortedTopics.get(0).split(" ");
        for (int i = 0; i < 7; i++) {
            topWords.add(splitedWords[i]);
        }
        splitedWords = sortedTopics.get(1).split(" ");
        for (int i = 0; i < 3; i++) {
            topWords.add(splitedWords[i]);
        }
        return topWords;
    }

    private List<String> getSortedListFromTopicsMap(Map<Double, String> topicProbabilitiesMap) {
        Map<Double, String> reverseSortedMap = new TreeMap<Double, String>(Collections.reverseOrder());
        reverseSortedMap.putAll(topicProbabilitiesMap);
        List<String> topicList = new ArrayList<>();
        reverseSortedMap.forEach((p, w) -> {
            topicList.add(w);
        });
        return topicList;
    }

    @Override
    public List<TopicProbability> getAuthorTopicProbability(String code) {
        List<TopicProbability> topicProbabilities = new ArrayList<>();
        List<AuthorTopicDistribution> authorTopicDistributions = authorTopicDistributionRepository.findAuthorTopic(
                Integer.parseInt(code));
        authorTopicDistributions.forEach(atd -> {
            topicProbabilities.add(new TopicProbability(atd.getTopic().getTopicCode(), atd.getProbability(),
                    Arrays.asList(atd.getTopic().getWordList().split(" "))));
        });
        return topicProbabilities;
    }

    @Override
    public List<String> getAuthorTopic(String code) {
        List<String> topicTopWords = getAuthorleTopWordsOfTopic(code);
        Set<String> keyword = getAuthorTopicKeywords(code);
        Set<String> ccs = getAuthorTopicCcs(code);
        List<String> mergedTopics = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger();
        counter.set(0);
        keyword.forEach(w -> {
            mergedTopics.add(w);
            counter.getAndIncrement();
            if (counter.get() > 10)
                return;
        });
        counter.set(0);
        ccs.forEach(w -> {
            mergedTopics.add(w);
            counter.getAndIncrement();
            if (counter.get() > 10)
                return;
        });
        counter.set(0);
        topicTopWords.forEach(w -> {
            mergedTopics.add(w);
            counter.getAndIncrement();
            if (counter.get() > 10)
                return;
        });

        return mergedTopics;

    }

    private List<String> getAuthorleTopWordsOfTopic(String code) {
        Map<Double, String> topicProbabilitiesMap = new HashMap<>();
        List<AuthorTopicDistribution> authorTopicDistributions = authorTopicDistributionRepository.findAuthorTopic(
                Integer.parseInt(code));
        authorTopicDistributions.forEach(atd -> {
            topicProbabilitiesMap.put(atd.getProbability(), atd.getTopic().getWordList());
        });
        List<String> sortedTopics = getSortedListFromTopicsMap(topicProbabilitiesMap);
        List<String> topWords = new ArrayList<>();
        String[] splitedWords = sortedTopics.get(0).split(" ");
        for (int i = 0; i < 7; i++) {
            topWords.add(splitedWords[i]);
        }
        splitedWords = sortedTopics.get(1).split(" ");
        for (int i = 0; i < 3; i++) {
            topWords.add(splitedWords[i]);
        }
        return topWords;
    }


    @Override
    public List<Author> getPredictedCoAuthors(Integer code) {
        List<Author> authors = new ArrayList<>();
        List<AuthorsPredictedLink> apl = authorPredictedLinkRepository.findByAuthorId((code));
        apl.forEach(link -> {
            if (link.getAuthor1().equals(code))
                authors.add(authorRepository.findById(link.getAuthor2()).get());
            else
                authors.add(authorRepository.findById(link.getAuthor1()).get());
        });
        return authors;
    }

    @Override
    public Set<Article> getRelatedArticles(String code) {

        Set<Author> authors = articleRepository.findArticleAuthors(Integer.parseInt(code)).getAuthors();
        Map<Article, Integer> articlesMap = createMapOfArticles(authors, Integer.parseInt(code));
        Map<Article, Integer> sortedAuthorsMap = sortArticleMapByValue(articlesMap);
        return sortedAuthorsMap.keySet();

    }

    private static Map<Article, Integer> sortArticleMapByValue(final Map<Article, Integer> wordCounts) {
        return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<Article, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<Article, Integer> createMapOfArticles(Set<Author> authors, Integer articleId) {
        Map<Article, Integer> articlesMap = new HashMap<>();
        authors.forEach((author -> {
            Set<Article> authorArticles = author.getArticles();
            authorArticles.forEach(article -> {
                if (article.getId().equals(articleId))
                    return;
                if (!articlesMap.containsKey(article)) {
                    articlesMap.put(article, 1);
                } else {
                    articlesMap.put(article, articlesMap.get(article) + 1);
                }
            });
        }));
        return articlesMap;
    }


    @Override
    public List<Article> getPredictedRelatedArticles(Integer code) {
        List<Article> articles = new ArrayList<>();
        List<ArticlesPredictedLink> apl = articlePredictedLinkRepository.findByArticleId((code));
        apl.forEach(link -> {
            if (link.getArticle1().equals(code))
                articles.add(articleRepository.findById(link.getArticle2()).get());
            else
                articles.add(articleRepository.findById(link.getArticle1()).get());
        });
        return articles;

    }

    @Override
    public List<PredictedLink> getPredictedLinks(PredictedLinksRequest predictedLinksRequest) {
        if (predictedLinksRequest.getGraph_type() == PredictedLinksRequest.GraphType.article) {
            return getArticlesPredictedLinks(predictedLinksRequest);
        } else if (predictedLinksRequest.getGraph_type() == PredictedLinksRequest.GraphType.author) {
            return getAuthorsPredictedLinks(predictedLinksRequest);
        }
        return null;
    }

    /*private List<PredictedLink> getArticlesPredictedLinks(PredictedLinksRequest predictedLinksRequest) {
        List<PredictedLink> predictedLinkList = new ArrayList<>();
        Pageable pageable = PageRequest.of(predictedLinksRequest.getPage(),
                predictedLinksRequest.getSize(), Sort.by("weight").descending());
        Page<ArticlesPredictedLink> page = articlePredictedLinkRepository.findAll(pageable);
        List<ArticlesPredictedLink> articlesPredictedLinks = page.getContent();
        articlesPredictedLinks.forEach(articlesPredictedLink -> {
            if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.ccs){
                predictedLinkList.add(createArticlesCssLink(articlesPredictedLink));
            }
            if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.keyword){
                predictedLinkList.add(createArticlesKeywordLink(articlesPredictedLink));
            }
*//*
            if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.topic_modeling){
                predictedLinkList.add(createArticlesTopicModelingLink(articlesPredictedLink));
            }
*//*
        });
        return predictedLinkList;

    }*/

    private List<PredictedLink> getArticlesPredictedLinks(PredictedLinksRequest predictedLinksRequest) {
        List<PredictedLink> predictedLinkList = new ArrayList<>();
        Pageable pageable = PageRequest.of(predictedLinksRequest.getPage(),
                predictedLinksRequest.getSize(), Sort.by("weight").descending());

        if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.ccs) {
            Page<ArticlesPredictedLink> page = articlePredictedLinkRepository.findAllTaxonomyLinks(pageable);
            List<ArticlesPredictedLink> articlesPredictedLinks = page.getContent();
            articlesPredictedLinks.forEach(articlesPredictedLink -> {
                predictedLinkList.add(
                        new PredictedLink(
                                new Node(articlesPredictedLink.getArticle1(), articlesPredictedLink.getTaxonomy1()),
                                new Node(articlesPredictedLink.getArticle2(), articlesPredictedLink.getTaxonomy2()),
                                articlesPredictedLink.getWeight()));
            });
        }
        if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.keyword) {
            Page<ArticlesPredictedLink> page = articlePredictedLinkRepository.findAllKeywordLinks(pageable);
            List<ArticlesPredictedLink> articlesPredictedLinks = page.getContent();
            articlesPredictedLinks.forEach(articlesPredictedLink -> {
                predictedLinkList.add(
                        new PredictedLink(
                                new Node(articlesPredictedLink.getArticle1(), articlesPredictedLink.getKeywords1()),
                                new Node(articlesPredictedLink.getArticle2(), articlesPredictedLink.getKeywords2()),
                                articlesPredictedLink.getWeight()));
            });
        }
        return predictedLinkList;

    }

    /*private PredictedLink createArticlesTopicModelingLink(ArticlesPredictedLink articlesPredictedLink) {
        List<String> topTopicWords1 = getArticleTopWordsOfTopic(String.valueOf(articlesPredictedLink.getArticle1()));
        List<String> topTopicWords2 = getArticleTopWordsOfTopic(String.valueOf(articlesPredictedLink.getArticle2()));

        PredictedLink predictedLink = new PredictedLink(
                new Node(articlesPredictedLink.getArticle1(), topTopicWords1),
                new Node(articlesPredictedLink.getArticle2(), topTopicWords2),
                articlesPredictedLink.getWeight());
        return predictedLink;
    }


    private PredictedLink createArticlesCssLink(ArticlesPredictedLink articlesPredictedLink) {
        Set<Taxonomy> article1Taxonomy = articleRepository.findArticleTaxonomies(articlesPredictedLink.getArticle1());
        Set<Taxonomy> article2Taxonomy = articleRepository.findArticleTaxonomies(articlesPredictedLink.getArticle2());
        List<String> taxonomeis1 = getTaxonomiesStringList(article1Taxonomy);
        List<String> taxonomeis2 = getTaxonomiesStringList(article2Taxonomy);
        PredictedLink predictedLink = new PredictedLink(
                new Node(articlesPredictedLink.getArticle1(), taxonomeis1),
                new Node(articlesPredictedLink.getArticle2(), taxonomeis2),
                articlesPredictedLink.getWeight());
        return predictedLink;
    }

    private PredictedLink createArticlesKeywordLink(ArticlesPredictedLink articlesPredictedLink) {
        Set<ArticleKeyword> article1Keyword = articleKeywordRepository.findByArticle_Id(articlesPredictedLink.getArticle1());
        Set<ArticleKeyword> article2Keyword = articleKeywordRepository.findByArticle_Id(articlesPredictedLink.getArticle2());
        List<String> keywords1 = getKeywordsStringList(article1Keyword);
        List<String> keywords2 = getKeywordsStringList(article2Keyword);
        PredictedLink predictedLink = new PredictedLink(
                new Node(articlesPredictedLink.getArticle1(), keywords1),
                new Node(articlesPredictedLink.getArticle2(), keywords2),
                articlesPredictedLink.getWeight());
        return predictedLink;
    }*/

    private List<String> getTaxonomiesStringList(Set<Taxonomy> article2Taxonomy) {
        List<String> taxonomiesString = new ArrayList<>();
        article2Taxonomy.forEach(taxonomy -> {
            taxonomiesString.add(taxonomy.getTitle());
        });
        return taxonomiesString;
    }

    private List<String> getKeywordsStringList(Set<ArticleKeyword> articleKeywords) {
        List<String> keywordsStringList = new ArrayList<>();
        articleKeywords.forEach(articleKeyword -> {
            keywordsStringList.add(articleKeyword.getKeyword());
        });
        return keywordsStringList;
    }

    private List<PredictedLink> getAuthorsPredictedLinks(PredictedLinksRequest predictedLinksRequest) {
        List<PredictedLink> predictedLinkList = new ArrayList<>();
        Pageable pageable = PageRequest.of(predictedLinksRequest.getPage(),
                predictedLinksRequest.getSize(), Sort.by("weight").descending());

        if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.ccs) {
            Page<AuthorsPredictedLink> page = authorPredictedLinkRepository.findAllTaxonomyLinks(pageable);
            List<AuthorsPredictedLink> authorsPredictedLinks = page.getContent();
            authorsPredictedLinks.forEach(authorsPredictedLink -> {
                predictedLinkList.add(
                        new PredictedLink(
                                new Node(authorsPredictedLink.getAuthor1(), authorsPredictedLink.getTaxonomy1()),
                                new Node(authorsPredictedLink.getAuthor2(), authorsPredictedLink.getTaxonomy2()),
                                authorsPredictedLink.getWeight()));
            });
        }
        if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.keyword) {
            Page<AuthorsPredictedLink> page = authorPredictedLinkRepository.findAllKeywordLinks(pageable);
            List<AuthorsPredictedLink> authorsPredictedLinks = page.getContent();
            authorsPredictedLinks.forEach(authorsPredictedLink -> {
                predictedLinkList.add(
                        new PredictedLink(
                                new Node(authorsPredictedLink.getAuthor1(), authorsPredictedLink.getKeywords1()),
                                new Node(authorsPredictedLink.getAuthor2(), authorsPredictedLink.getKeywords2()),
                                authorsPredictedLink.getWeight()));
            });
        }
        return predictedLinkList;
    }

/*
    private List<PredictedLink> getAuthorsPredictedLinks(PredictedLinksRequest predictedLinksRequest) {
        List<PredictedLink> predictedLinkList = new ArrayList<>();
        Pageable pageable = PageRequest.of(predictedLinksRequest.getPage(),
                predictedLinksRequest.getSize(), Sort.by("weight").descending());
        Page<AuthorsPredictedLink> page = authorPredictedLinkRepository.findAll(pageable);
        List<AuthorsPredictedLink> authorssPredictedLinks = page.getContent();
        authorssPredictedLinks.forEach(authorsPredictedLink -> {
            if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.ccs) {
                predictedLinkList.add(createAuthorsCssLink(authorsPredictedLink));
            }
            if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.keyword) {
                predictedLinkList.add(createAuthorsKeywordLink(authorsPredictedLink));
            }
*/
/*
            if (predictedLinksRequest.getMethod() == PredictedLinksRequest.Method.topic_modeling){
                predictedLinkList.add(createAuthorsTopicModelingLink(authorsPredictedLink));
            }
*//*

        });
        return predictedLinkList;
    }
*/

    /*private PredictedLink createAuthorsCssLink(AuthorsPredictedLink articlesPredictedLink) {
        Set<Taxonomy> authors1Taxonomy = authorRepository.findAuthorTaxonomies(articlesPredictedLink.getAuthor1());
        Set<Taxonomy> authors2Taxonomy = authorRepository.findAuthorTaxonomies(articlesPredictedLink.getAuthor2());
        List<String> taxonomeis1 = getTaxonomiesStringList(authors1Taxonomy);
        List<String> taxonomeis2 = getTaxonomiesStringList(authors2Taxonomy);
        PredictedLink predictedLink = new PredictedLink(
                new Node(articlesPredictedLink.getAuthor1(), taxonomeis1),
                new Node(articlesPredictedLink.getAuthor2(), taxonomeis2),
                articlesPredictedLink.getWeight());
        return predictedLink;
    }

    private PredictedLink createAuthorsKeywordLink(AuthorsPredictedLink articlesPredictedLink) {
        Set<ArticleKeyword> author1Keyword = articleKeywordRepository.findAuthorKeywords(articlesPredictedLink.getAuthor1());
        Set<ArticleKeyword> author2Keyword = articleKeywordRepository.findAuthorKeywords(articlesPredictedLink.getAuthor2());
        List<String> keywords1 = getKeywordsStringList(author1Keyword);
        List<String> keywords2 = getKeywordsStringList(author2Keyword);
        PredictedLink predictedLink = new PredictedLink(
                new Node(articlesPredictedLink.getAuthor1(), keywords1),
                new Node(articlesPredictedLink.getAuthor2(), keywords2),
                articlesPredictedLink.getWeight());
        return predictedLink;
    }

    private PredictedLink createAuthorsTopicModelingLink(AuthorsPredictedLink articlesPredictedLink) {
        List<String> topTopicWords1 = getAuthorleTopWordsOfTopic(String.valueOf(articlesPredictedLink.getAuthor1()));
        List<String> topTopicWords2 = getAuthorleTopWordsOfTopic(String.valueOf(articlesPredictedLink.getAuthor2()));

        PredictedLink predictedLink = new PredictedLink(
                new Node(articlesPredictedLink.getAuthor1(), topTopicWords1),
                new Node(articlesPredictedLink.getAuthor2(), topTopicWords2),
                articlesPredictedLink.getWeight());
        return predictedLink;
    }*/

}
