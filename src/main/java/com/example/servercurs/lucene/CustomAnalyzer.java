package com.example.servercurs.lucene;

import lombok.SneakyThrows;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ru.RussianLightStemFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.tartarus.snowball.ext.RussianStemmer;

public class CustomAnalyzer extends Analyzer {
    @SneakyThrows
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {

        SynonymMap.Builder builder = createSynonymMap();
        SynonymMap synonymMap = builder.build();

        Tokenizer source = new StandardTokenizer();
        TokenStream filter = new SynonymGraphFilter(source, synonymMap, true);
        filter = new LowerCaseFilter(filter);
        filter = new RussianLightStemFilter(filter);
        filter = new SnowballFilter(filter, new RussianStemmer());
        // Добавьте другие фильтры здесь
        return new TokenStreamComponents(source, filter);
    }

    private SynonymMap.Builder createSynonymMap() {
        SynonymMap.Builder builder = new SynonymMap.Builder(true);

// Добавьте синонимы для слов, которые могут использоваться в каталоге курсов
        builder.add(new CharsRef("java"), new CharsRef("j2ee"), true);
        builder.add(new CharsRef("python"), new CharsRef("py"), true);
        builder.add(new CharsRef("javascript"), new CharsRef("js"), true);
        builder.add(new CharsRef("c++"), new CharsRef("cpp"), true);
        builder.add(new CharsRef("ruby"), new CharsRef("rails"), true);
        builder.add(new CharsRef("html"), new CharsRef("html5"), true);
        builder.add(new CharsRef("css"), new CharsRef("css3"), true);
// Добавьте синонимы для слов, которые могут использоваться в каталоге курсов
        builder.add(new CharsRef("лекция"), new CharsRef("семинар"), true);
        builder.add(new CharsRef("лекция"), new CharsRef("вебинар"), true);
        builder.add(new CharsRef("лекция"), new CharsRef("мастер-класс"), true);
        builder.add(new CharsRef("обучение"), new CharsRef("инструктаж"), true);
        builder.add(new CharsRef("обучение"), new CharsRef("тренинг"), true);
        builder.add(new CharsRef("начальный уровень"), new CharsRef("для начинающих"), true);
        builder.add(new CharsRef("начальный уровень"), new CharsRef("базовый уровень"), true);
        builder.add(new CharsRef("начальный уровень"), new CharsRef("вводный курс"), true);
        builder.add(new CharsRef("продвинутый уровень"), new CharsRef("для опытных"), true);
        builder.add(new CharsRef("продвинутый уровень"), new CharsRef("высокий уровень"), true);
        builder.add(new CharsRef("продвинутый уровень"), new CharsRef("глубокое погружение"), true);
        builder.add(new CharsRef("сертификат"), new CharsRef("диплом"), true);
        builder.add(new CharsRef("сертификат"), new CharsRef("лицензия"), true);
        builder.add(new CharsRef("сертификат"), new CharsRef("аттестат"), true);
        builder.add(new CharsRef("студент"), new CharsRef("учащийся"), true);
        builder.add(new CharsRef("студент"), new CharsRef("слушатель"), true);
        builder.add(new CharsRef("студент"), new CharsRef("обучающийся"), true);
        builder.add(new CharsRef("преподаватель"), new CharsRef("инструктор"), true);
        builder.add(new CharsRef("преподаватель"), new CharsRef("лектор"), true);
        builder.add(new CharsRef("преподаватель"), new CharsRef("тьютор"), true);
        builder.add(new CharsRef("домашнее задание"), new CharsRef("задание"), true);
        builder.add(new CharsRef("домашнее задание"), new CharsRef("упражнение"), true);
        builder.add(new CharsRef("домашнее задание"), new CharsRef("практическая работа"), true);
        builder.add(new CharsRef("экзамен"), new CharsRef("тест"), true);
        builder.add(new CharsRef("экзамен"), new CharsRef("оценка"), true);
        builder.add(new CharsRef("экзамен"), new CharsRef("проверка знаний"), true);
        builder.add(new CharsRef("онлайн-курс"), new CharsRef("дистанционное обучение"), true);
        builder.add(new CharsRef("онлайн-курс"), new CharsRef("веб-курс"), true);
        builder.add(new CharsRef("онлайн-курс"), new CharsRef("e-learning"), true);
        builder.add(new CharsRef("программирование"), new CharsRef("разработка"), true);
        builder.add(new CharsRef("базы данных"), new CharsRef("SQL"), true);
        builder.add(new CharsRef("базы данных"), new CharsRef("БД"), true);
        builder.add(new CharsRef("машинное обучение"), new CharsRef("искусственный интеллект"), true);
        return builder;
    }

}
