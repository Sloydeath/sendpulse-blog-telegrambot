package by.github.sendpulse.sptb.parser;

import by.github.sendpulse.sptb.entity.SubscriptionGroup;
import by.github.sendpulse.sptb.entity.User;
import by.github.sendpulse.sptb.service.interfaces.SendBotMessageService;
import by.github.sendpulse.sptb.service.interfaces.SubscriptionGroupService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SendPulseParser {

    private static final Logger log = Logger.getLogger(SendPulseParser.class);

    public final SubscriptionGroupService subscriptionGroupService;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public SendPulseParser(SubscriptionGroupService subscriptionGroupService, SendBotMessageService sendBotMessageService) {
        this.subscriptionGroupService = subscriptionGroupService;
        this.sendBotMessageService = sendBotMessageService;

        scheduleParsing();
    }

    private void scheduleParsing() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            try {
                doParsing();
                log.info("SendPulse site was parsed ");
            } catch (IOException e) {
                log.log(Level.ERROR, e);
            }
        }, 0, 15, TimeUnit.MINUTES);
    }

    //this method does parsing of pages of groups
    public void doParsing() throws IOException {
        String url;
        int lastArticleId;
        List<SubscriptionGroup> subscriptionGroupList = subscriptionGroupService.findAll();

        if (subscriptionGroupList != null) {
            for (SubscriptionGroup subscriptionGroup: subscriptionGroupList) {
                url = subscriptionGroup.getUrl();
                lastArticleId = subscriptionGroup.getLastArticleId();
                List<String> articleInfo = parsingSitePage(url);
                if (Integer.parseInt(articleInfo.get(0)) != lastArticleId) {
                    subscriptionGroup.setLastArticleId(Integer.parseInt(articleInfo.get(0)));
                    subscriptionGroupService.update(subscriptionGroup);

                    Set<User> subscribedUsers = subscriptionGroup.getUsers();
                    if (subscribedUsers != null) {
                        for (User user : subscribedUsers) {
                            if (user.isActive()) {
                                String chatId = user.getId().toString();
                                String message = String.format(
                                        "Ура! Появилась новая статья в разделе %s!\n\n" +
                                                "<b>%s</b>\n" +
                                                "Скорее жми на ссылку, чтобы прочитать:\n" +
                                                "%s",
                                        subscriptionGroup.getName(), articleInfo.get(1), articleInfo.get(2));
                                sendBotMessageService.sendMessage(chatId, message);
                            }
                        }
                    }
                }
            }
        }
    }

    //this method parse group page and return info about last article ([id], [title of article], [url of article])
    private List<String> parsingSitePage(String url) throws IOException {
        log.info("Parser has connected to url = " + url);
        Document doc = Jsoup.connect(url).get();
        String id = doc.getElementsByTag("article").attr("class").split(" ")[0].substring(5);
        String articleTitle = doc.getElementsByTag("article").get(0)
                .getElementsByClass("title").get(0).select("a[href]").first().text();
        String articleUrl = doc.getElementsByTag("article").get(0).getElementsByClass("title").get(0)
                .select("a[href]").attr("href");
        return new ArrayList<>(Arrays.asList(id, articleTitle, articleUrl));
    }
}