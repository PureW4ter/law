package com.destinesia.base;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.destinesia.entity.Code;

public class CVSExport {
	/** CSV文件列分隔符 */
	private static final String CSV_COLUMN_SEPARATOR = ",";
	/** CSV文件列分隔符 */
	private static final String CSV_RN = "\r\n";

	public static boolean doExport(List<Code> dataList, OutputStream os) {
		try {
			StringBuffer buf = new StringBuffer();
			// 输出列头
			buf.append("可用券码").append(CSV_COLUMN_SEPARATOR).append("推荐人手机").append(CSV_COLUMN_SEPARATOR).append("用户等级");
			buf.append(CSV_RN);
			// 输出数据
			for (Code code : dataList) {
				buf.append(code.getCode()).append(CSV_COLUMN_SEPARATOR)
						.append(code.getIdentity()).append(CSV_COLUMN_SEPARATOR)
						.append(code.getGrade());
				buf.append(CSV_RN);
			}
			// 写出响应
			os.write(buf.toString().getBytes("gb2312"));
			os.flush();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * @throws UnsupportedEncodingException
	 * 
	 *             setHeader
	 */
	public static void responseSetProperties(String fileName, HttpServletResponse response)
			throws UnsupportedEncodingException {
		// 设置文件后缀
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fn = fileName + sdf.format(new Date()).toString() + ".csv";
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, "gb2312"));
		
	}

}