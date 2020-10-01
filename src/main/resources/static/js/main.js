function getIndexOfRegion(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

const regionApi = Vue.resource('/region{/id}');

Vue.component('region-form', {
    props: ['regions', 'regionAttr'],

    data: function () {
        return {
            name: null,
            shortName: null,
            id: ''
        }
    },

    watch: {
        regionAttr: function (newValue) {
            this.name = newValue.name
            this.shortName = newValue.shortName
            this.id = newValue.id
        }
    },

    template:
        '<div>' +
        '<el-input type="text" placeholder="Write name" v-model="name" required/>' +
        '<el-input style="margin-left: 5px; position: absolute" type="text" placeholder="Write short name" v-model="shortName" required/>' +
        '<div><el-button style="margin-top: 5px" icon="el-icon-check" type="success" @click="save" round /></div>' +
        '</div>',

    methods: {
        save: function () {
            const region = {name: this.name, shortName: this.shortName}
            if (this.id) {
                regionApi.update({id: this.id}, region).then(result =>
                    result.json().then(data => {
                        const indexOfRegion = getIndexOfRegion(this.regions, data.id)
                        this.regions.splice(indexOfRegion, 1, data)
                        this.name = ''
                        this.shortName = ''
                        this.id = ''
                    })
                )
            } else {
                regionApi.save({}, region).then(result => {
                    result.json().then(data => {
                        this.regions.push(data)
                        this.name = ''
                        this.shortName = ''
                    })
                })
            }
        }
    }
})

Vue.component('region-row', {
    props: ['region', 'editMethod', 'regions'],

    template:
        '<tr>' +
        '<td>{{ region.name }}</td>' +
        '<td>{{ region.shortName }}</td>' +
        '<td>' +
        '<el-button icon="el-icon-edit" @click="edit" round/>' +
        '</td>' +
        '<td>' +
        '<el-button icon="el-icon-delete" @click="del" round/>' +
        '</td>' +
        '</tr>',

    methods: {
        edit: function () {
            this.editMethod(this.region)
        },

        del: function () {
            regionApi.remove({id: this.region.id}).then(result => {
                if (result.ok) {
                    this.regions.splice(this.regions.indexOf(this.region), 1)
                }
            })
        }
    }
})

Vue.component('regions-list', {
    props: ['regions'],

    data: function () {
        return {
            region: null
        }
    },

    template:
        '<div style="position: relative; width: 400px">' +
        '<region-form :regions="regions" :regionAttr="region" />' +
        '<table>' +
        '<thead><tr><th style="padding-right: 10px">Name of region</th> <th>Short name of region</th></tr></thead>' +
        '<region-row v-for="region in regions" :key="region.id" :region="region" :regions="regions" :editMethod="editMethod" />' +
        '</table>' +
        '</div>',

    created: function () {
        regionApi.get().then(result =>
            result.json().then(data =>
                data.forEach(region => this.regions.push(region))
            )
        )
    },

    methods: {
        editMethod: function (region) {
            this.region = region
        }
    }
})

new Vue({
    el: '#app',
    template: '<regions-list :regions="regions" />',
    data: {
        regions: []
    }
})