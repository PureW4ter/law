package com.jfzy.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.jfzy.service.bo.WxPayResponseDto;
import com.jfzy.service.exception.JfApplicationRuntimeException;

public class XmlUtil {

	public static String parseXml(Object obj) {

		StringWriter sw = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(obj, sw);
			return sw.toString();
		} catch (JAXBException e) {
			throw new JfApplicationRuntimeException("Malformed vo of parseXml", e);
		} finally {
			try {
				sw.close();
			} catch (IOException e) {
			}
		}
	}

	public static Object fromXml(String xml, Class clazz) {
		StringReader sr = new StringReader(xml);
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);

			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(sr);
		} catch (JAXBException e) {
			throw new JfApplicationRuntimeException("Malformed vo of parseXml", e);
		} finally {
			sr.close();
		}
	}
}
