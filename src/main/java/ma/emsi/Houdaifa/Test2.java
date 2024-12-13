package ma.emsi.Houdaifa;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;

import java.util.Map;

public class Test2 {
    public static void main(String[] args) {
        // Clé API
        String apiKey = System.getenv("GEMINI_KEY");

        // Modèle LLM
        ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-1.5-flash")
                .build();

        // Création du template pour la traduction
        PromptTemplate promptTemplate = PromptTemplate.from("Traduis le texte suivant en {{langue}} : {{texte}}");

        // Application des variables
        Prompt prompt = promptTemplate.apply(Map.of(
                "langue", "italien",
                "texte", "Bonjour, comment allez-vous ?"
        ));

        // Génération de la réponse
        String traduction = model.generate(prompt.text());

        // Affichage
        System.out.println("Traduction : " + traduction);
    }
}
