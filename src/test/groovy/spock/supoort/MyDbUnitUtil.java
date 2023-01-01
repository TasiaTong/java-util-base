package spock.supoort;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.ClassPathResource;

public class MyDbUnitUtil {

    public static IDataSet loadCsv(Class<?> testClass, String dir)
            throws DataSetException, IOException {
        URL url = testClass.getResource("");
        File file = new File(url.getPath() + dir);
        if (file.exists() & file.isDirectory()) {
            return loadCsv(url.getPath() + dir);
        }
        return loadCsv(dir);
    }

    public static IDataSet loadCsv(String dirFromClassPath) throws DataSetException, IOException {
        String absolutionDirPath = new ClassPathResource(dirFromClassPath).getURL().getPath();
        return new CsvDataSet(new File(absolutionDirPath));
    }

    public static IDataSet loadXml(Class<?> testClass, String location)
            throws DataSetException, IOException {
        URL url = testClass.getResource("");
        File file = new File(url.getPath() + location);
        if (file.exists() && file.isFile()) {
            return loadXml(url.getPath() + location);
        }
        return loadXml(location);
    }

    public static IDataSet loadXml(String fileFromClassPath) throws DataSetException, IOException {
        String absoluteFilePath = new ClassPathResource(fileFromClassPath).getURL().getPath();
        return new FlatXmlDataSetBuilder().build(new File(absoluteFilePath));
    }
}
