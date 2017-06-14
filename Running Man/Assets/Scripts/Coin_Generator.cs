using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Coin_Generator : MonoBehaviour
{
	public int coinsCount = 50;
	//public float minDistanse = 10;
	public Transform coinObject;

	void Start()
	{
		for (int i = 0; i < coinsCount; i++) {
			float x = Random.value * 500f;
			float z = Random.value * 500f;
			Vector3 pos = new Vector3 (x, 1f, z);
			Instantiate (coinObject, pos, transform.rotation);
		}
	}
}
