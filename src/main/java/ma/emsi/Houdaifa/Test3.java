package ma.emsi.Houdaifa;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test3 {
    public static void main(String[] args) {
        // Clé API
        String apiKey = System.getenv("GEMINI_KEY");

        // Vérification de la clé API
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("Erreur : La clé GEMINI_KEY n'est pas définie.");
            return;
        }

        // Création du modèle d'embeddings avec "text-embedding-004"
        EmbeddingModel embeddingModel = new GoogleAiEmbeddingModel(
                "text-embedding-004", // Nom du modèle
                apiKey,
                300, // Taille des vecteurs (à vérifier selon le modèle)
                GoogleAiEmbeddingModel.TaskType.SEMANTIC_SIMILARITY, // Type de tâche
                "", // Région (facultatif)
                200, // Timeout en millisecondes
                Duration.ofSeconds(10), // Timeout total
                true // Logging activé
        );

        // Phrases à comparer
        String phrase1 = "Bonjour, comment allez-vous ?";
        String phrase2 = "J'aime le croissant";

        try {
            // Génération des embeddings
            Embedding embedding1 = embeddingModel.embed(phrase1).content();
            Embedding embedding2 = embeddingModel.embed(phrase2).content();

            // Calcul de la similarité cosinus
            double similarity = CosineSimilarity.between(embedding1, embedding2);

            // Affichage des résultats
            System.out.println("Phrase 1 : " + phrase1);
            System.out.println("Phrase 2 : " + phrase2);
            System.out.println("Similarité cosinus : " + similarity);
        } catch (Exception e) {
            System.err.println("Erreur lors du traitement des embeddings : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
