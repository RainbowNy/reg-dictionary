<#import "parts/base.ftl" as base>

<@base.page>
    <div>
        List of regions
        <table>
            <#list regions as region>
                <tr>
                    <td>${region.id}</td>
                    <td>${region.name}</td>
                    <td>${region.shortName}</td>
                </tr>
            </#list>
        </table>
    </div>
</@base.page>