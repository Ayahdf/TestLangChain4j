package ma.emsi.Houdaifa;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class Test1 {
    public static void main(String[] args) {
        //Test 1 : pour se chauffer...

        String cle = System.getenv("GEMINI_KEY");
        ChatLanguageModel modele = GoogleAiGeminiChatModel
                .builder()
                .apiKey(cle)
                .modelName("gemini-1.5-flash")
                .build();
        String reponse = modele.generate("Capitale du Maroc ?");
        System.out.println(reponse);
    }
}
