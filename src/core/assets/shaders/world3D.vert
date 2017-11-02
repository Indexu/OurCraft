#ifdef GL_ES
precision mediump float;
#endif

const int numberOfLights = 3;

attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_uv;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_lightPosition[numberOfLights];
uniform vec4 u_eyePosition;

varying vec2 v_uv;
varying vec4 v_n;
varying vec4 v_s[numberOfLights];
varying vec4 v_h[numberOfLights];
varying float v_len_v;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;

	// Global coordinates

	v_n = normal;

	vec4 v = u_eyePosition - position;
	v_len_v = length(v);

//	v.x = v.x / v_len_v;
//	v.y = v.y / v_len_v;
//	v.z = v.z / v_len_v;

	for (int i = 0; i < numberOfLights; i++)
	{
	    v_s[i] = u_lightPosition[i] - position;
	    v_h[i] = v_s[i] + v;
	}

	position = u_viewMatrix * position;
	// normal = u_viewMatrix * normal;

	// Eye coordinates

	// v_color = (dot(normal, vec4(0,0,1,0)) / length(normal)) * u_color;
	// v_color = (dot(normal, normalize(vec4(-position.x,-position.y,-position.z,0))) / length(normal)) * u_color;

    v_uv = a_uv;
	gl_Position = u_projectionMatrix * position;

	// Clip coordinates
}