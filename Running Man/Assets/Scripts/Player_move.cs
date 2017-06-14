using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityStandardAssets.CrossPlatformInput;

public class Player_move : MonoBehaviour
{
	public float moving_Sens = 0.1f;
	public float rotation_Sens = 5f;
	Animator animator;
	// Use this for initialization
	void Start()
	{
		animator = GetComponent<Animator> ();
	}
	
	// Update is called once per frame
	void Update()
	{
		float move = CrossPlatformInputManager.GetAxis ("Vertical");
		float rotate = CrossPlatformInputManager.GetAxis ("Horizontal");
		float freezeAxis = 0.0f;
		//Vector3 move = new Vector3 (freezeAxis, freezeAxis, moveZ);
		Vector3 rotation = new Vector3 (freezeAxis, rotate, freezeAxis);
		animator.SetFloat ("Forward", move);
		transform.Rotate (rotation_Sens * rotation, Space.Self);
	}
}
