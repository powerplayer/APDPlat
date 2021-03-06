package com.apdplat.platform.action;

import com.apdplat.platform.model.Model;
import com.apdplat.platform.result.Page;
import com.apdplat.platform.service.Service;
import com.apdplat.platform.util.SpringContextUtils;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 *
 *控制器接口的抽象实现类
 *
 * @author 杨尚川
 */
public abstract class AbstractAction<T extends Model, S extends Service<T>>  extends ActionSupport implements Action {

    protected S service = null;
    protected T model = null;
    protected Page<T> page = new Page<>();
    @Resource(name = "springContextUtils")
    protected SpringContextUtils springContextUtils;

    @PostConstruct
    private void initService() {
        if (this.service == null) {
            this.service = (S) springContextUtils.getBean(getDefaultServiceName());
        }
    }

    @PostConstruct
    private void initModel() {
        if (this.model == null) {
            this.model = (T) springContextUtils.getBean(getDefaultModelName());
        }
    }
    private String getDefaultServiceName(){
        return getDefaultModelName()+"Service";
    }

    private String getDefaultModelName(){
        return getDefaultModelName(getClass());
    }

    /**
     * 在添加及更新一个特定的完整的Model之前对Model的组装，以确保组装之后的Model是一个语义完整的模型
     * @return
     */
    public T assemblyModel(T model) {
        return model;
    }

    @Override
    public String create() {
        service.create(assemblyModel(model));

        super.setFeedback(new Feedback(model.getId(), "添加成功"));

        return SUCCESS;
    }

    @Override
    public String createForm() {
        return FORM;
    }

    @Override
    public String retrieve() {
        this.setModel(service.retrieve(model.getId()));

        return DETAIL;
    }

    @Override
    public String updateForm() {
        setModel(service.retrieve(model.getId()));
        return null;
    }

    @Override
    public String updatePart() {
        service.update(model.getId(), getPartProperties(model));

        super.setFeedback(new Feedback(model.getId(), "添加成功"));

        return SUCCESS;
    }

    @Override
    public String updateWhole() {
        service.update(assemblyModel(model));

        super.setFeedback(new Feedback(model.getId(), "更新成功"));

        return SUCCESS;
    }

    @Override
    public String delete() {
        service.delete(super.getIds());

        return SUCCESS;
    }

    @Override
    public String query() {
        this.setPage(service.query(super.getPageCriteria(), super.buildPropertyCriteria(), super.buildOrderCriteria()));
        return LIST;
    }

    @Override
    public String search() {
        return null;
    }

    public T getModel() {
        return this.model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }
}
