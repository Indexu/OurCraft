
#ifdef GL_ES
precision mediump float;
#endif

attribute vec2 a_position;

uniform mat4 u_modelMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_color;

varying vec4 v_color;

void main()
{
	vec4 color;

	vec4 position = vec4(a_position.x, a_position.y, 0.0, 1.0);
	position = u_modelMatrix * position;

	color = u_color;

	v_color = color;

	gl_Position = u_projectionMatrix * position;
}