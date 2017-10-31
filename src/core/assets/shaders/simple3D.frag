
#ifdef GL_ES
precision mediump float;
#endif

const int numberOfLights = 6;

struct light
{
    vec4 color;
    vec4 direction;
    float spotFactor;
    float constantAttenuation;
    float linearAttenuation;
    float quadraticAttenuation;
    float on;
};

uniform float u_shininessFactor;
uniform float u_spotFactor;

uniform vec4 u_globalAmbience;

uniform light u_lights[numberOfLights];

uniform sampler2D u_diffuseTexture;
uniform float u_usesDiffuseTexture;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_materialAmbience;
uniform vec4 u_materialEmission;
uniform float u_materialTransparency;

uniform float u_brightness;

varying vec2 v_uv;
varying vec4 v_n;
varying vec4 v_s[numberOfLights];
varying vec4 v_h[numberOfLights];

void main()
{
    vec4 materialDiffuse;
	if(u_usesDiffuseTexture == 1.0)
	{
		materialDiffuse = texture2D(u_diffuseTexture, v_uv);  //also * u_materialDiffuse ??? up to you.
		//materialDiffuse *= u_materialDiffuse;
	}
	else
	{
		materialDiffuse = u_materialDiffuse;
	}

    // Lighting

    vec4 color;
    float len_n = length(v_n);

    for (int i = 0; i < numberOfLights; i++)
    {
        if (u_lights[i].on == 0.0)
        {
            continue;
        }

        float len_s = length(v_s[i]);
        float attinuation = 1.0;

        if (u_lights[i].spotFactor != 0.0)
        {
            float spotAttenuation = dot(-v_s[i], u_lights[i].direction) / (len_s * length(u_lights[i].direction));
            spotAttenuation = (spotAttenuation < 0.0 ? 0.0 : pow(spotAttenuation, u_lights[i].spotFactor));

            if (spotAttenuation == 0.0)
            {
                continue;
            }

            float distanceAttenuation = 1.0 / (u_lights[i].constantAttenuation + len_s * u_lights[i].linearAttenuation + len_s * len_s * u_lights[i].quadraticAttenuation);

            attinuation = distanceAttenuation * spotAttenuation;
        }

        vec4 lightColor;

    	float lampert = dot(v_n, v_s[i]) / (len_n * len_s);
    	lampert = ((lampert < 0.0) ? 0.0 : lampert);

    	float phong = dot(v_n, v_h[i]) / (len_n * length(v_h[i]));
        phong = (phong < 0.0 ? 0.0 : pow(phong, u_shininessFactor));


        lightColor += u_lights[i].color * materialDiffuse * lampert;
        lightColor += u_lights[i].color * u_materialSpecular * phong;

        lightColor *= attinuation;

        color += lightColor;
    }

    color += u_globalAmbience * materialDiffuse;
    color += u_materialEmission;
    color.a = u_materialTransparency;

	gl_FragColor = color * u_brightness;
}