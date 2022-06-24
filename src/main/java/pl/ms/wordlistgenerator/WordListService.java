package pl.ms.wordlistgenerator;

import org.springframework.stereotype.Service;


@Service
class WordListService {
    private InputFilesService inputFileService;

    public WordListService(InputFilesService inputFileService) {
        this.inputFileService = inputFileService;
        String sqlSentenceStatement = inputFileService.covertDataToSqlQuery("corpus_sentences.txt");
        String sqlDictStatement = inputFileService.covertDataToSqlQuery("dictionary.txt");
        inputFileService.exportToDataSql(sqlSentenceStatement, sqlDictStatement);
    }
}
