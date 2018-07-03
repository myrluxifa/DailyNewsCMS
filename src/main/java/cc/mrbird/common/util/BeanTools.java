package cc.mrbird.common.util;

import java.beans.PropertyDescriptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class BeanTools {
	
	/**
	 * @title getContent 
	 * @description 获取按字母排序并用&拼接字段串
	 * @author Easy
	 * @date 2017年11月4日 下午4:20:33
	 * @param obj
	 * @return String
	 */
	public static String getContent(Object obj) {
		
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(obj.getClass());
		String result = "";
		try {
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if("class".equals(name) || "sign".equals(name)) {
					continue;
				}
				Object value = pd.getReadMethod().invoke(obj);
				if (StringUtils.isBlank(result)) {
					if(null != value)
						result += name + "=" + value;
				} else {
					if(null != value)	
						result += "&" + name + "=" + value;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * @title toXml 
	 * @description 实体转Xml
	 * @author Easy
	 * @date 2017年11月4日 下午9:33:14
	 * @param obj
	 * @return String
	 */
	public static String toXml(Object obj) {
		XStream xstream = new XStream();
		xstream.processAnnotations(obj.getClass());
		return xstream.toXML(obj).replaceAll("__", "_");
	}
	
	/**
	 * @title fromXml 
	 * @description xml转实体
	 * @author Easy
	 * @date 2017年11月10日 下午8:03:45
	 * @param xml
	 * @param clazz
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xml, Class<T> clazz) {
		XStream xstream = new XStream() {
			@Override
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@Override
					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn,
							String fieldName) {
						if (definedIn == Object.class) {
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xstream.alias("xml", clazz);
		return (T) xstream.fromXML(xml);
	}
}
