package com.pokkt.demo.fragments.osvad;

/**
 * Created by abhinavrathore019 on 21/11/17.
 */

public class NativeWebAdJs {

    static String nativewebad_js = "var agentOSVAD;\n" +
            "var isOSVADAgentVisible;\n" +
            "\n" +
            "\n" +
            "function removeAgent() {\n" +
            "    if (document.body.contains(agentOSVAD)) {\n" +
            "        document.body.removeChild(agentOSVAD);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function setAgentSize(width, height) {\n" +
            "    var element = document.getElementsByTagName('body')[0];\n" +
            "    var style = window.getComputedStyle(element);\n" +
            "    var marginLeft = parseInt(style.marginLeft);\n" +
            "    var marginRight = parseInt(style.marginRight);\n" +
            "\n" +
            "    var adjustedWidth = width - marginLeft - marginRight;\n" +
            "    console.log(\"marginLeft: \" + marginLeft + \", marginRight: \" + marginRight + \", adjustedWidth: \" + adjustedWidth);\n" +
            "\n" +
            "    agentOSVAD.style.width = adjustedWidth;\n" +
            "    agentOSVAD.style.height = height;\n" +
            "    //updateAgentBounds();\n" +
            "}\n" +
            "\n" +
            "function updateAgentBounds() {\n" +
            "    if (agentOSVAD) {\n" +
            "        var rect = agentOSVAD.getBoundingClientRect();\n" +
            "        console.log(rect.top, rect.right, rect.bottom, rect.left);\n" +
            "        PokktOSVAD.updateAgentBounds(rect.left, rect.top, rect.right, rect.bottom);\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function setAgentBGColor(value) {\n" +
            "    if (agentOSVAD) {\n" +
            "        agentOSVAD.style.backgroundColor = value;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "function findLocationForAd() {\n" +
            "    setAgentBGColor(\"#FF00FF\");\n" +
            "    //setAgentSize(30, 1); // initial\n" +
            "    updateAgentBounds();\n" +
            "}\n" +
            "\n" +
            "function initPokktOSVAD() {\n" +
            "    // locate an element with id \"osvad\", it has to be of type \"CENTER\"\n" +
            "    agentOSVAD = document.getElementById(\"osvad\");\n" +
            "\n" +
            "    // if not found, create a new one and put it in the host page\n" +
            "    if (!agentOSVAD || agentOSVAD.nodeName != \"CENTER\") {\n" +
            "        agentOSVAD = document.createElement(\"center\");\n" +
            "        agentOSVAD.id = \"osvad\";\n" +
            "        document.body.appendChild(agentOSVAD);\n" +
            "        console.log(\"element osvad is created!\");\n" +
            "    } else {\n" +
            "        console.log(\"element osvad is already available!\");\n" +
            "    }\n" +
            "\n" +
            "    // this will also ensure the SDK that script is ready\n" +
            "    PokktOSVAD.pokktOSVADInitDone();\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/**\n" +
            " * helpers\n" +
            " **/\n" +
            "\n" +
            "function isElementInViewport(el) {\n" +
            "    var rect = el.getBoundingClientRect();\n" +
            "    return (\n" +
            "        rect.top >= 0 &&\n" +
            "        rect.left >= 0 &&\n" +
            "        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&\n" +
            "        rect.right <= (window.innerWidth || document.documentElement.clientWidth)\n" +
            "    );\n" +
            "}\n" +
            "\n" +
            "function updateVisibility() {\n" +
            "    if (agentOSVAD) {\n" +
            "        var newVisibility = isElementInViewport(agentOSVAD);\n" +
            "        if (newVisibility != isOSVADAgentVisible) {\n" +
            "            isOSVADAgentVisible = newVisibility;\n" +
            "\n" +
            "            // visibility changed, notify\n" +
            "            PokktOSVAD.setAgentVisible(isOSVADAgentVisible);\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/**\n" +
            " * prepare handlers\n" +
            " **/\n" +
            "\n" +
            "var handler = function () {\n" +
            "    updateVisibility();\n" +
            "    if (agentOSVAD && isOSVADAgentVisible) {\n" +
            "        updateAgentBounds();\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/**\n" +
            " * attach listeners\n" +
            " **/\n" +
            "\n" +
            "if (window.addEventListener) {\n" +
            "    addEventListener('scroll', handler, false);\n" +
            "    addEventListener('resize', handler, false);\n" +
            "} else if (window.attachEvent) {\n" +
            "    attachEvent('onscroll', handler);\n" +
            "    attachEvent('onresize', handler);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/**\n" +
            " * init agent\n" +
            " **/\n" +
            "initPokktOSVAD();\n";
}
