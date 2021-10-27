package org.ok.starfish.data.content.provider.account;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountsCsvReader {

    private final AccountsCsvLineReader csvLineReader;

    @Value("classpath:data/snp500.csv")
    private Resource file;

    public AccountsCsvReader(AccountsCsvLineReader accountsCsvLineReader) {
        this.csvLineReader = accountsCsvLineReader;
    }

    public @NotNull List<AccountLine> read() {
        List<AccountLine> result = new ArrayList<>();
        try {
            Reader reader = new FileReader(file.getFile());
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .withSkipLines(1)  //skip header line
                    .build();
            List<String[]> lines = csvReader.readAll();
            for (String[] line : lines) {
                result.add(csvLineReader.read(line));
            }
            reader.close();
            csvReader.close();
        }
        catch (Exception e) {
            log.error(String.format("Failed to read accounts from CSV file %s", file.getFilename()), e);
        }
        return result;
    }
}
