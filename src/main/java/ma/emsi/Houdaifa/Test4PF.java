package ma.emsi.Houdaifa;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class Test4PF {

    // Interface de l'assistant
    interface Assistant {
        String chat(String userMessage);
    }

    public static void main(String[] args) {
        // Clé API du LLM
        String llmKey = System.getenv("GEMINI_KEY");

        // Configuration du modèle conversationnel
        ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
                .temperature(0.7)
                .modelName("gemini-1.5-flash")
                .apiKey(llmKey)
                .build();

        // Chargement du fichier PDF en tant que document
        String pdfFile = "langchain4j.pdf";
        Document document = FileSystemDocumentLoader.loadDocument(pdfFile);

        // Création d'une base vectorielle en mémoire
        InMemoryEmbeddingStore embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(document, embeddingStore);

        // Création de l'assistant conversationnel avec une mémoire de conversation et un récupérateur de contenu
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();

        // Exemple de questions posées au LLM
        System.out.println("** Questions et réponses : **");

        // Question 1
        String question1 = "Quel est l'objectif du cours 'Machine Learning' de Richard Grin ?";
        String response1 = assistant.chat(question1);
        System.out.println("Question : " + question1);
        System.out.println("Réponse : " + response1);

        // Question 2
        String question2 = "Sur quel slide avez-vous trouvé cette information ?";
        String response2 = assistant.chat(question2);
        System.out.println("\nQuestion : " + question2);
        System.out.println("Réponse : " + response2);

        // Question 3
        String question3 = "Pouvez-vous créer un quiz à choix multiples sur le Machine Learning ?";
        String response3 = assistant.chat(question3);
        System.out.println("\nQuestion : " + question3);
        System.out.println("Réponse : " + response3);

        // Question 4
        String question4 = "Quels sont les algorithmes abordés dans ce support ?";
        String response4 = assistant.chat(question4);
        System.out.println("\nQuestion : " + question4);
        System.out.println("Réponse : " + response4);
    }
}
