package com.example.administrator.test4;

import android.util.Log;

import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Blsver {
	private static Field G1,G2;	//G1Ⱥ
	private static Field GT;	//G2Ⱥ
	private static Field Zr;	//����Ⱥ
	private static Pairing pairing;
	public static boolean verify(byte[] data ,byte[] genElement,byte[] publicKey,byte[] sign) throws Exception {

		//��ʼ��˫����Ⱥ,���ļ�a.properties�ж�ȡ������ʼ��˫����Ⱥ
		Pairing pairing = PairingFactory.getPairing("assets/a160.properties");
				//ʵ����˫����Ⱥ
		PairingFactory.getInstance().setUsePBCWhenPossible(true);
				//��ʼ��Ⱥ
		Zr = pairing.getZr();	//ZrΪ����Ⱥ
		G1 = pairing.getG1();	//G1Ϊ�ӷ�Ⱥ
		GT = pairing.getGT();

		//ȡ��Կ
		Element pubkey = G1.newZeroElement();
		pubkey.setFromBytes(publicKey);
		//System.out.println("ȡ�õĹ�ԿΪ��"+pubkey);
		//ȡ����Ԫ
		Element genEle = G1.newZeroElement();
		genEle.setFromBytes(genElement);
		//System.out.println("�õ�����ԪΪ��"+genEle);
		//ȡǩ��
		Element sig = G1.newZeroElement();
		sig.setFromBytes(sign);
		//��Ϣ�Ĺ�ϣ
		Element h = pairing.getG1().newElementFromHash(data, 0, data.length);
		//����T1

		Element T1 = GT.newZeroElement();
		T1 =  pairing.pairing(genEle, sig).getImmutable();
		Log.v("T1",T1.toString());
		//����T2
		Element T2 = GT.newZeroElement();
		T2 = pairing.pairing(pubkey, h).getImmutable();
		Log.v("T2",T2.toString());
		boolean b;
		if(T1.equals(T2)) {
			b = true;
		}else {
			b=false;
		}
		return b;
		
	}
//	public static void main(String[] args) throws DecoderException {
//		 byte[] a = {35, 68, -3, 81, 110, 37, -93, 61, 121, -77, -94, 3, -107, -103, 11, -65, 111, 93, 10, -50, -94, 25};
//         byte[] b = {40, 116, -101, -124, -109, -73, -23, -105, -9, -12, 105, 45, -53, -62, 124, 54, -90, 110, -69, -70, -62, -114};
//         String result= "˵��/sig=EJWanpQ9LKK5wJMgA2nM2zQ4+VEdqg==";
//         String [] index = result.split("/sig=");
//         String message = index[0];
//         String signature = index[1];
//         try {
//			System.out.println(verify(message.getBytes(), a, b, Base64.decodeBase64(signature)));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
