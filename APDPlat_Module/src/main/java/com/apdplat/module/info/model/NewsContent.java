package com.apdplat.module.info.model;

import com.apdplat.platform.annotation.ModelAttr;
import com.apdplat.platform.generator.ActionGenerator;
import com.apdplat.platform.model.Model;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Scope("prototype")
@Component
@Searchable
public class NewsContent extends Model{
    @ManyToOne
    @ModelAttr("新闻")
    protected News news;
    
    @Enumerated(EnumType.STRING) 
    protected Lang lang;
    
    @SearchableProperty
    @ModelAttr("标题")
    protected String title;
    @Lob
    @SearchableProperty
    @ModelAttr("内容")
    protected String content;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }    

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    @Override
    public String getMetaData() {
        return "新闻多语言内容";
    }
    public static void main(String[] args){
        NewsContent obj=new NewsContent();
        //生成Action
        ActionGenerator.generate(obj.getClass());
    }
}
