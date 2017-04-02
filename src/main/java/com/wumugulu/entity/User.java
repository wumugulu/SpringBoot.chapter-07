package com.wumugulu.entity;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(value=Include.NON_NULL)
@JsonPropertyOrder(alphabetic=true)
@JsonIgnoreProperties(value={"address"}, ignoreUnknown=false)
public class User {

	private Long id;
	private String username;
	@JsonProperty(access=Access.WRITE_ONLY )
	private String password;
	@JsonSerialize(using=MySexSerializer.class)
	@JsonDeserialize(using=MySexDeserializer.class)
	private Integer sex;
	// 序列化和反序列化时都会忽略对象的这个属性
	@JsonIgnore
	private String address;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date birthday;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", birthday=" + birthday
				+ ", sex=" + sex + ", address=" + address + "]";
	}
	
}

// 序列化 - 把Integer类型的sex字段变换为String输出
// 响应的，需要给sex属性添加注解：@JsonSerialize(using=MySexSerializer.class)
final class MySexSerializer extends JsonSerializer<Integer> {
	@Override
	public void serialize(Integer value, JsonGenerator jsonGenerator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String sexString = "";
		if(value.intValue()%2 == 1){
			sexString = "this is a 帅哥";
		} else {
			sexString = "that is a 靓妹";
		}
		jsonGenerator.writeString(sexString);
	}
}

// 反序列化 - 把输入的sex内容当做String读入，经过运算之后转换为Integer赋值对象的sex属性
// 响应的，需要给sex属性添加注解：@JsonDeserialize(using=MySexDeserializer.class)
final class MySexDeserializer extends JsonDeserializer<Integer> {
	@Override
	public Integer deserialize(JsonParser jsonParser, DeserializationContext context) 
			throws IOException, JsonProcessingException {
		Integer newValue;
		String value = jsonParser.getValueAsString();
		System.out.println("value="+value);
		if(value.indexOf("帅哥")>=0){
			newValue = 1;
		} else {
			newValue = 2;
		}
		return newValue;
	}

}

