<template>
    <div class="outBoX">
        <sider-nav :navList="navList"></sider-nav>
        <div class="mainBox">
            <div class="browseHelp helpBox">
                <div class="title">Browse</div>
                <p>In the Browse module, users can quickly search for projects 
                    or samples in which they are interested based on criteria.</p>
                <img src="../../assets/img/browse.png" alt="">
                <P>Here, we use the search project as a case study to 
                    show users how to use the quick search feature of the browse module.</P>
                <p>1. Country selection bar: China, USA, Italy, etc.</p>
                <p>2. Pig type selection field: boar, sow, fattening pig,
                    piglet. (Hint: we grouped all hog research projects into these four categories)</p>
                <p>3. Breed selection field: select different pig breeds (Duroc, etc.).</p>
                <p>4. Growth stage selection
                    bar: select the growth stage.</p>
                <p>5. Sampling location selection field: stomach, small intestine, faeces,
                    etc.</p>
                <p>6. Phenotype selection field: meat quality, growth rate, semen quality, etc.</p>
                <p>7. Submit: After selecting specific criteria, 
                    the user clicks the submit button to search the database for research projects that meet the goal.</p>
            </div>
            <div class="searchHelp helpBox">
                <div class="title">Search</div>
                <p>In the search module, users can search for projects,
                     samples and microbes of interest according to their specific requirements.</p>
                <img src="../../assets/img/search.png" alt="">
                <p>As shown in the above image, the search module is divided into two parts. 
                    The first part is the single value search column and the second part is the multi-value search column. 
                    Let's use a simple case study to help users use the search module.</p>
                <p>1. First, we introduce the single-value search section, in the search target section, the user can select items, samples or microorganisms. Then, enter the target value in the search box section and click the search button. 
                    The database will search for data based on the target value to return to the user.
                </p>
                <p>2.Secondly, in the multi-value search section (advanced search), in the query condition area, 
                    we have prepared 8 Query conditions (phenotype, experimental design,
                    species, country, category, sex, sampling location, growth stage) 
                    for the user. Users can customize how many search conditions to choose 
                    (Hint, please try to choose 2-3 conditions, because more than one condition to choose, 
                    there may be no matching data), and for each search condition field,
                    we have prepared a drop-down pre-selected value in advance, 
                    which is convenient for users to choose quickly.
                    </p>
                <p>3.The Clear button will help the user to clear the search conditions, 
                    so that the user can choose other conditions; the user clicks on the Submit button, 
                    the database will return the expected data according to the query target value. 
                </p>
            </div>
            <div class="dataHelp helpBox">
                <div class="title">Data</div>
                <p>For the data in the PGMD database, we have created a number of data distribution maps, 
                    including: the number of samples based on the four pig categories, 
                    statistics of the projects by country, statistics of the sampling locations of the samples, 
                    statistics of the number of different microbes under the six levels of classification 
                    (microbes, archaea, and microbes that could not be identified), 
                    and of course, we have created a map of the world based on the amount of data from the samples. 
                    A world map has been created to allow the user to visualize the distribution of pig research projects around the world.</p> 
                <img src="../../assets/img/upload.png" alt="">
                <p>The page also includes a data upload and feedback module (hint, data upload requires prior registration and login to the database), 
                    which is designed to call scholars in the field of swine research to work together to build the PGMD.</p>
                    <p>
                        We have carefully designed a data upload form, 
                        including the contributor's email (which is very important), 
                        data type, paper title, project number, paper description, analysis results, etc.
                        For the uploaded data types. We also provide some template forms and files,
                        which can be downloaded locally by clicking on "Examples",
                        so that users can share public research results through this form. 
                        Finally, if users have any pertinent suggestions,
                        they can fill in the feedback area, or contact us through the following e-mail.
                    </p>
            </div>
            <div class="toolsHelp helpBox">
                <div class="title">Tools</div>
                <p>Tools provides a number of self-designed tools that focus on specific problems and application scenarios
                    in the hog sector.</p>
            </div>
            <div class="contactHelp helpBox">
                <div class="title">Contact</div>
                <p>We welcome users to help us build PGMD, and if you have any excellent suggestions for the site
                    , you can contact us at the following e-mail address.   <br>
                    E-mail address: Zaiwen.Feng@mail.hzau.edu.cn</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

//侧边导航栏
const navList = ref([
    {
        name: 'Browse',
        location: '.browseHelp'
    },
    {
        name: 'Search',
        location: '.searchHelp'
    },
    {
        name: 'Data',
        location: '.dataHelp'
    },
    {
        name: 'Tools',
        location: '.toolsHelp'
    },
    {
        name: 'Contact',
        location: '.contactHelp'
    },
])

</script>

<style scoped lang="less">
.outBox {
    width: 100%;
}

.mainBox {
    width: 80%;
    margin-left: 20%;

    .helpBox {
        width: 100%;
        // min-height: 500px;

        margin-top: 50px;
        background-color: #F7F7F7;
        padding: 50px;
        border-radius: 50px;
        .title {
            font-size: 26px;
            font-weight: bold;
            margin-bottom: 20px;
        }
        p {
            font-size: 18px;
            line-height: 30px;
        }

        img {
            width: 100%;
            margin-top: 20px;
        }
    }
}</style>