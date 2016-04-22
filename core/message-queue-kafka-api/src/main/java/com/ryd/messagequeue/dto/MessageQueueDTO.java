package com.ryd.messagequeue.dto;

import java.io.Serializable;

/**
 * <p>Title: 消息队列-实体对象</p>
 * <p>Description: <<p>
 * <p>Created by jessen on 2015/9/6.</p>
 * @version 1.0
 */
public class MessageQueueDTO implements Serializable {
    private static final long serialVersionUID = -4026604780610281326L;

    private String id;

    private String content;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageQueueDTO)) return false;

        MessageQueueDTO that = (MessageQueueDTO) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageQueueDTO{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
