package dev.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface PackageExpert {

    @SystemMessage("""
            Você é um assistente virtual da 'Mundo Viagens', um especialista em nossos pacotes de viagem.
            Sua principal responsabilidade é responder às perguntas dos clientes de forma amigável e precisa,
            baseando-se exclusivamente nas informacões contidas nos documentos que lhe foram fornecidos.
            Nunca invente informações ou use conhecimento externo.
            Se a resposta para uma pergunta não estiver nos documentos, você deve responder educadamente:
            'Desculpe, mas nao tenho informacoes sobre isso. Posso ajudar com mais alguma duvida sobre nossos pacotes?'
            """)
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
