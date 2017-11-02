#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_diffuseTexture;
uniform vec4 u_globalAmbience;

varying vec2 v_uv;

void main()
{
	gl_FragColor = texture2D(u_diffuseTexture, v_uv) * u_globalAmbience;
}